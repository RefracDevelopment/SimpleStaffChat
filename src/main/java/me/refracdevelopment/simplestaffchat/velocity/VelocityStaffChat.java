package me.refracdevelopment.simplestaffchat.velocity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.velocity.commands.*;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminHideCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevHideCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.velocity.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.velocity.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.LuckPermsUtil;
import net.luckperms.api.LuckPermsProvider;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

@Getter
@Plugin(id = "simplestaffchat2",
        name = "SimpleStaffChat2",
        version = "3.2-rc.3",
        dependencies = {@Dependency(id = "signedvelocity", optional = true), @Dependency(id = "luckperms", optional = true)},
        url = "https://discord.gg/EFeSKPg739",
        description = "A Simple StaffChat Plugin",
        authors = "RefracDevelopment")
public class VelocityStaffChat {

    @Getter private static VelocityStaffChat instance;
    private final Metrics.Factory metricsFactory;
    private final ProxyServer server;
    private final Logger logger;
    private final Path path;

    // Files
    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;

    // Caches
    private Config config;
    private Commands commands;
    private Discord discord;

    @Inject
    public VelocityStaffChat(ProxyServer server, @DataDirectory Path path, Logger logger, Metrics.Factory metricsFactory) {
        instance = this;
        this.server = server;
        this.logger = logger;
        this.path = path;
        this.metricsFactory = metricsFactory;

        loadFiles();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        long startTiming = System.currentTimeMillis();
        PluginManager pluginManager = getServer().getPluginManager();
        PluginContainer container = pluginManager.getPlugin("simplestaffchat2").get();

        metricsFactory.make(this, 12096);

        if (!getSignedVelocityAddon()) {
            Color.log("<red>If you get kicked out in 1.19+ while typing in a staffchat on Velocity, " +
                    "consider downloading https://modrinth.com/plugin/signedvelocity");
        }

        if (pluginManager.isLoaded("luckperms")) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            Color.log("<yellow>Hooked into LuckPerms.");
        }

        loadCommands();
        loadListeners();

        Color.log("<gray><strikethrough>==<red><strikethrough>=====<white><strikethrough>======================<red><strikethrough>=====<gray><strikethrough>==");
        Color.log("<yellow>" + container.getDescription().getName().get() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" <white>[*] <gold>Version<white>: <aqua>" + container.getDescription().getVersion().get());
        Color.log(" <white>[*] <gold>Name<white>: <aqua>" + container.getDescription().getName().get());
        Color.log(" <white>[*] <gold>Author<white>: <aqua>" + container.getDescription().getAuthors().get(0));
        Color.log("<gray><strikethrough>==<red><strikethrough>=====<white><strikethrough>======================<red><strikethrough>=====<gray><strikethrough>==");

        updateCheck(server.getConsoleCommandSource(), true);
    }

    private void loadFiles() {
        // Files
        configFile = new ConfigFile("velocity-config.yml");
        commandsFile = new ConfigFile("commands.yml");
        discordFile = new ConfigFile("discord.yml");

        // Caches
        config = new Config();
        commands = new Commands();
        discord = new Discord();
    }
    
    public void reloadFiles() {
        // Files
        getConfigFile().reload();
        getCommandsFile().reload();
        getDiscordFile().reload();

        // Cache
        getConfig().loadConfig();
        getCommands().loadConfig();
        getDiscord().loadConfig();
    }

    private void loadCommands() {
        if (getCommands().STAFFCHAT_COMMAND_ENABLED) {
            final String[] aliases_staffchat = getCommands().STAFFCHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().STAFFCHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_staffchat)
                    .build(), new StaffChatCommand(this));
        }

        if (getCommands().STAFF_TOGGLE_COMMAND_ENABLED) {
            final String[] aliases_toggle = getCommands().STAFF_TOGGLE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().STAFF_TOGGLE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_toggle)
                    .build(), new ToggleCommand(this));
        }

        getServer().getCommandManager().register(getServer().getCommandManager()
                        .metaBuilder("staffchatreload")
                        .aliases("screload")
                        .build(), new ReloadCommand(this));

        if (getCommands().ADMINCHAT_COMMAND_ENABLED) {
            final String[] aliases_adminchat = getCommands().ADMINCHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().ADMINCHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_adminchat)
                    .build(), new AdminChatCommand(this));
        }

        if (getCommands().ADMIN_TOGGLE_COMMAND_ENABLED) {
            final String[] aliases_adminchat_toggle = getCommands().ADMIN_TOGGLE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().ADMIN_TOGGLE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_adminchat_toggle)
                    .build(), new AdminToggleCommand(this));
        }

        if (getCommands().DEVCHAT_COMMAND_ENABLED) {
            final String[] aliases_devchat = getCommands().DEVCHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().DEVCHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_devchat)
                    .build(), new DevChatCommand(this));
        }

        if (getCommands().DEV_TOGGLE_COMMAND_ENABLED) {
            final String[] aliases_devchat_toggle = getCommands().DEV_TOGGLE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().DEV_TOGGLE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_devchat_toggle)
                    .build(), new DevToggleCommand(this));
        }

        if (getCommands().CHAT_COMMAND_ENABLED) {
            final String[] aliases_chat = getCommands().CHAT_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().CHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_chat)
                    .build(), new ChatCommand(this));
        }

        if (getCommands().STAFF_HIDE_COMMAND_ENABLED) {
            final String[] aliases_hide = getCommands().STAFF_HIDE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().STAFF_HIDE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_hide)
                    .build(), new HideCommand(this));
        }

        if (getCommands().ADMIN_HIDE_COMMAND_ENABLED) {
            final String[] aliases_admin_hide = getCommands().ADMIN_HIDE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().CHAT_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_admin_hide)
                    .build(), new AdminHideCommand(this));
        }

        if (getCommands().DEV_HIDE_COMMAND_ENABLED) {
            final String[] aliases_dev_hide = getCommands().DEV_HIDE_COMMAND_ALIASESES.toArray(new String[0]);
            getServer().getCommandManager().register(getServer().getCommandManager()
                    .metaBuilder(getCommands().DEV_HIDE_COMMAND_ALIASESES.get(0))
                    .aliases(aliases_dev_hide)
                    .build(), new DevHideCommand(this));
        }

        Color.log("<yellow>Loaded commands.");
    }

    private void loadListeners() {
        server.getEventManager().register(this, new ChatListener());
        server.getEventManager().register(this, new JoinListener());
        Color.log("<yellow>Loaded listeners.");
    }

    @SuppressWarnings("ALL")
    public boolean getSignedVelocityAddon() {
        return getServer().getPluginManager().isLoaded("signedvelocity");
    }

    public void updateCheck(CommandSource sender, boolean console) {
        try {
            PluginContainer container = getServer().getPluginManager().getPlugin("simplestaffchat2").get();

            String urlString = "https://refracdev-updatecheck.refracdev.workers.dev/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            while ((input = reader.readLine()) != null) {
                response.append(input);
            }
            reader.close();
            JsonObject object = new JsonParser().parse(response.toString()).getAsJsonObject();

            if (object.has("plugins")) {
                JsonObject plugins = object.get("plugins").getAsJsonObject();
                JsonObject info = plugins.get(container.getDescription().getName().get()).getAsJsonObject();
                String version = info.get("version").getAsString();
                if (version.equals(container.getDescription().getVersion().get())) {
                    if (console) {
                        Color.sendMessage(sender, "<green>" + container.getDescription().getName().get() + " is on the latest version.");
                    }
                } else {
                    Color.sendMessage(sender, " ");
                    Color.sendMessage(sender, " ");
                    Color.sendMessage(sender, "<red>Your " + container.getDescription().getName().get() + " version is out of date!");
                    Color.sendMessage(sender, "<red>We recommend updating ASAP!");
                    Color.sendMessage(sender, "");
                    Color.sendMessage(sender, "<red>Your Version: <yellow>" + container.getDescription().getVersion().get());
                    Color.sendMessage(sender, "<green>Newest Version: <yellow>" + version);
                    Color.sendMessage(sender, " ");
                    Color.sendMessage(sender, " ");
                    return;
                }
            } else {
                Color.sendMessage(sender, "<red>Wrong response from update API, contact plugin developer!");
            }
        } catch (
                Exception ex) {
            Color.sendMessage(sender, "<red>Failed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}