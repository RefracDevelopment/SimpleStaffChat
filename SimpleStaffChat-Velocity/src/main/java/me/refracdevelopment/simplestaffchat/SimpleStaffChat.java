package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.commands.*;
import me.refracdevelopment.simplestaffchat.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.config.cache.Servers;
import me.refracdevelopment.simplestaffchat.config.cache.VelocityConfig;
import me.refracdevelopment.simplestaffchat.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.utilities.Metrics;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.utilities.chat.LuckPermsUtil;
import net.luckperms.api.LuckPermsProvider;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

@Getter
public class SimpleStaffChat {

    @Getter
    private static SimpleStaffChat instance;
    private final Metrics.Factory metricsFactory;
    private final ProxyServer server;
    private final Logger logger;
    private final Path path;

    // Files
    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;
    private ConfigFile localeFile;
    private ConfigFile serversFile;

    // Caches
    private VelocityConfig config;
    private Commands commands;
    private Discord discord;
    private Servers servers;

    @Inject
    public SimpleStaffChat(ProxyServer server, @DataDirectory Path path, Logger logger, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.path = path;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;

        metricsFactory.make(this, 21704);

        long startTiming = System.currentTimeMillis();
        PluginManager pluginManager = getServer().getPluginManager();
        PluginContainer container = pluginManager.getPlugin("simplestaffchat").get();

        loadFiles();

        LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
        Color.log("&aHooked into LuckPerms.");

        loadModules();

        Color.log("&7&m==&r&c&m=====&r&f&m======================&r&c&m=====&r&7&m==");
        Color.log("&e" + container.getDescription().getName().get() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" &f[*] <gold>Version<white>: &b" + container.getDescription().getVersion().get());
        Color.log(" &f[*] <gold>Name<white>: &b" + container.getDescription().getName().get());
        Color.log(" &f[*] <gold>Author<white>: &b" + container.getDescription().getAuthors().get(0));
        Color.log("&7&m==&r&c&m=====&r&f&m======================&r&c&m=====&r&7&m==");

        updateCheck(server.getConsoleCommandSource(), true);
    }

    private void loadFiles() {
        // Files
        configFile = new ConfigFile("config.yml", true);
        commandsFile = new ConfigFile("commands.yml", true);
        discordFile = new ConfigFile("discord.yml", true);
        localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml", true);
        serversFile = new ConfigFile("servers.yml", true);

        // Caches
        config = new VelocityConfig();
        commands = new Commands();
        discord = new Discord();
        servers = new Servers();

        Color.log("&c==========================================");
        Color.log("&aAll files have been loaded correctly!");
        Color.log("&c==========================================");
    }

    private void loadModules() {
        if (getConfig().STAFFCHAT_ENABLED) {
            final String[] aliases_staffchat = getCommands().STAFFCHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().STAFFCHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_staffchat)
                    .build(), new StaffChatCommand(this));
        }

        if (getConfig().ADMINCHAT_ENABLED) {
            final String[] aliases_adminchat = getCommands().ADMINCHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().ADMINCHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_adminchat)
                    .build(), new AdminChatCommand(this));
        }

        if (getConfig().DEVCHAT_ENABLED) {
            final String[] aliases_devchat = getCommands().DEVCHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().DEVCHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_devchat)
                    .build(), new DevChatCommand(this));
        }

        if (getConfig().CHAT_TOGGLE_ENABLED) {
            final String[] aliases_toggle = getCommands().STAFF_TOGGLE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().STAFF_TOGGLE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_toggle)
                    .build(), new ToggleCommand(this));

            final String[] aliases_adminchat_toggle = getCommands().ADMIN_TOGGLE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().ADMIN_TOGGLE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_adminchat_toggle)
                    .build(), new AdminToggleCommand(this));

            final String[] aliases_devchat_toggle = getCommands().DEV_TOGGLE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().DEV_TOGGLE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_devchat_toggle)
                    .build(), new DevToggleCommand(this));

            final String[] aliases_chat = getCommands().CHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().CHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_chat)
                    .build(), new ChatCommand(this));

            server.getEventManager().register(this, new ChatListener());
        }

        if (getConfig().JOIN_ENABLED)
            server.getEventManager().register(this, new JoinListener());

        final String[] aliases_hide = getCommands().HIDE_COMMAND_ALIASESES.toArray(new String[0]);
        getServer().getCommandManager().register(getServer().getCommandManager()
                .metaBuilder(getCommands().HIDE_COMMAND_ALIASESES.get(0))
                .aliases(aliases_hide)
                .build(), new HideCommand(this));

        getServer().getCommandManager().register(getServer().getCommandManager()
                .metaBuilder("staffchatreload")
                .aliases("screload")
                .build(), new ReloadCommand(this));

        Color.log("&aLoaded modules.");
    }

    public void updateCheck(CommandSource sender, boolean console) {
        try {
            PluginContainer container = getServer().getPluginManager().getPlugin("simplestaffchat").get();

            String urlString = "https://refracdev-updatecheck.refracdev.workers.dev/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            StringBuilder response = new StringBuilder();

            while ((input = reader.readLine()) != null) {
                response.append(input);
            }

            reader.close();
            JsonObject object = JsonParser.parseString(response.toString()).getAsJsonObject();

            if (object.has("plugins")) {
                JsonObject plugins = object.get("plugins").getAsJsonObject();
                JsonObject info = plugins.get(container.getDescription().getName().get()).getAsJsonObject();
                String version = info.get("version").getAsString();
                boolean archived = info.get("archived").getAsBoolean();

                if (archived) {
                    sender.sendMessage(Color.translate("&cThis plugin has been marked as &e&b'Archived' &r&cby RefracDevelopment."));
                    sender.sendMessage(Color.translate("&cThis version will continue to work but will not receive updates or support."));
                } else if (version.equals(container.getDescription().getVersion().get())) {
                    if (console) {
                        sender.sendMessage(Color.translate("&a" + container.getDescription().getName().get() + " is on the latest version."));
                    }
                } else {
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour " + container.getDescription().getName().get() + " version &7(" + container.getDescription().getVersion().get() + ") &cis out of date! Newest: &e&bv" + version));
                    sender.sendMessage(Color.translate(""));
                }
            } else {
                sender.sendMessage(Color.translate("&cWrong response from update API, contact plugin developer!"));
            }
        } catch (Exception ex) {
            sender.sendMessage(Color.translate("&cFailed to get updater check. (" + ex.getMessage() + ")"));
        }
    }
}