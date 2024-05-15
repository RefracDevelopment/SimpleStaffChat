package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginContainer;
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
import me.refracdevelopment.simplestaffchat.config.cache.Config;
import me.refracdevelopment.simplestaffchat.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.config.cache.Servers;
import me.refracdevelopment.simplestaffchat.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.utilities.chat.LuckPermsUtil;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.luckperms.api.LuckPermsProvider;
import org.bstats.velocity.Metrics;
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
    private PluginContainer container;

    // Files
    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;
    private ConfigFile localeFile;
    private ConfigFile serversFile;

    // Caches
    private Config config;
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

        container = server.getPluginManager().getPlugin("simplestaffchat").get();

        metricsFactory.make(this, 21704);

        loadFiles();

        RyMessageUtils.sendConsole(false,
                "<#A020F0> _____ _           _     _____ _       ___ ___ _____ _       _     " + "Running <#7D0DC3>v" + container.getDescription().getVersion().get(),
                "<#A020F0>|   __|_|_____ ___| |___|   __| |_  __|  _|  _|     | |_  __| |_   " + "Server <#7D0DC3>" + getServer().getVersion().getName() + " <#A020F0>v" + getServer().getVersion().getVersion(),
                "<#A020F0>|__   | |     | . | | -_|__   |  _||. |  _|  _|   --|   ||. |  _|  " + "Discord support: <#7D0DC3>" + container.getDescription().getUrl().get(),
                "<#7D0DC3>|_____|_|_|_|_|  _|_|___|_____| | |___|_| |_| |_____|_|_|___| |    " + "Thanks for using my plugin ❤ !",
                "<#7D0DC3>              |_|             |__|                          |__|",
                "        <#A020F0>Developed by <#7D0DC3>RefracDevelopment",
                ""
        );

        loadModules();
        loadHooks();

        updateCheck();
    }

    private void loadFiles() {
        // Files
        configFile = new ConfigFile("config.yml");
        commandsFile = new ConfigFile("commands.yml");
        discordFile = new ConfigFile("discord.yml");
        localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml");
        serversFile = new ConfigFile("servers.yml");

        // Caches
        config = new Config();
        commands = new Commands();
        discord = new Discord();
        servers = new Servers();

        RyMessageUtils.sendConsole(true, "&aLoaded all files.");
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

            final String[] aliases_hide = getCommands().HIDE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().HIDE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_hide)
                    .build(), new HideCommand(this));

            server.getEventManager().register(this, new ChatListener());
        }

        if (getConfig().JOIN_ENABLED)
            server.getEventManager().register(this, new JoinListener());

        getServer().getCommandManager().register(getServer().getCommandManager()
                .metaBuilder("staffchatreload")
                .aliases("screload")
                .build(), new ReloadCommand(this));

        RyMessageUtils.sendConsole(true, "&aLoaded modules.");
    }

    private void loadHooks() {
        if (getServer().getPluginManager().isLoaded("luckperms")) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            RyMessageUtils.sendConsole(true, "&aHooked into LuckPerms.");
        }
    }

    public void updateCheck() {
        try {
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
                    RyMessageUtils.sendConsole(true, "&cThis plugin has been marked as &e&b'Archived' &r&cby RefracDevelopment.");
                    RyMessageUtils.sendConsole(true, "&cThis version will continue to work but will not receive updates or support.");
                } else if (version.equals(container.getDescription().getVersion().get())) {
                    RyMessageUtils.sendConsole(true, "&a" + container.getDescription().getName().get() + " is on the latest version.");
                } else {
                    RyMessageUtils.sendConsole(true, "&cYour " + container.getDescription().getName().get() + " version &7(" + container.getDescription().getVersion().get() + ") &cis out of date! Newest: &e&bv" + version);
                }
            } else {
                RyMessageUtils.sendConsole(true, "&cWrong response from update API, contact plugin developer!");
            }
        } catch (Exception ex) {
            RyMessageUtils.sendConsole(true, "&cFailed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}