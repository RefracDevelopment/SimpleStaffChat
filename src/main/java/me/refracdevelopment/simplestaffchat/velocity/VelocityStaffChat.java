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
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.api.VelocityStaffChatAPI;
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
import me.refracdevelopment.simplestaffchat.velocity.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.LuckPermsUtil;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Placeholders;
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
        version = "3.2-beta.5",
        dependencies = {@Dependency(id = "unsignedvelocity", optional = true), @Dependency(id = "luckperms", optional = true)},
        url = "https://discord.gg/EFeSKPg739",
        description = "A Simple StaffChat Plugin",
        authors = "RefracDevelopment")
public class VelocityStaffChat {

    private final Metrics.Factory metricsFactory;
    private final ProxyServer server;
    private final Logger logger;
    private final Path path;

    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;
    
    private Config config;
    private Commands commands;
    private Discord discord;

    private Color color;
    private Placeholders placeholders;
    private DiscordImpl discordImpl;
    private Methods methods;
    private Permissions permissions;
    private LuckPermsUtil luckPermsUtil;

    private VelocityStaffChatAPI staffChatAPI;

    @Inject
    public VelocityStaffChat(ProxyServer server, @DataDirectory Path path, Logger logger, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.path = path;
        this.metricsFactory = metricsFactory;
    }

    @Inject
    public PluginContainer container;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        long startTiming = System.currentTimeMillis();

        loadFiles();

        metricsFactory.make(this, 12096);
        this.color = new Color(this);
        this.placeholders = new Placeholders(this);
        this.permissions = new Permissions();
        this.discordImpl = new DiscordImpl(this);
        this.methods = new Methods(this);
        this.staffChatAPI = new VelocityStaffChatAPI(this);

        this.color.log("<red>==========================================");
        this.color.log("<yellow>All files have been loaded correctly!");
        this.color.log("<red>==========================================");

        if (!getUnsignedVelocityAddon()) {
            this.color.log("<red>If you get kicked out in 1.19+ while typing in a staffchat on Velocity, " +
                    "consider downloading https://github.com/4drian3d/UnSignedVelocity/releases/latest");
        }

        if (getConfig().LUCKPERMS || server.getPluginManager().isLoaded("luckperms")) {
            this.luckPermsUtil = new LuckPermsUtil(this);
            this.luckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            this.color.log("<yellow>Hooked into LuckPerms.");
        }

        loadCommands();
        loadListeners();

        this.color.log("<gray><strikethrough>==<red><strikethrough>=====<white><strikethrough>======================<red><strikethrough>=====<gray><strikethrough>==");
        this.color.log("<yellow>" + getContainer().getDescription().getName().get() + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        this.color.log(" <white>[*] <gold>Version<white>: <aqua>" + getContainer().getDescription().getVersion().get());
        this.color.log(" <white>[*] <gold>Name<white>: <aqua>" + getContainer().getDescription().getName().get());
        this.color.log(" <white>[*] <gold>Author<white>: <aqua>" + getContainer().getDescription().getAuthors().get(0));
        this.color.log("<gray><strikethrough>==<red><strikethrough>=====<white><strikethrough>======================<red><strikethrough>=====<gray><strikethrough>==");

        updateCheck(server.getConsoleCommandSource(), true);
    }

    public void loadFiles() {
        this.configFile = new ConfigFile(path, "velocity-config.yml");
        this.commandsFile = new ConfigFile(path, "velocity-commands.yml");
        this.discordFile = new ConfigFile(path, "discord.yml");
        this.configFile.reload();
        this.commandsFile.reload();
        this.discordFile.reload();
        this.config = new Config(this);
        this.commands = new Commands(this);
        this.discord = new Discord(this);
    }

    public void reloadFiles() {
        this.configFile.reload();
        this.commandsFile.reload();
        this.discordFile.reload();
        this.config.loadConfig();
        this.commands.loadConfig();
        this.discord.loadConfig();
        this.color.log("<red>==========================================");
        this.color.log("<yellow>All files have been loaded correctly!");
        this.color.log("<red>==========================================");
    }

    private void loadCommands() {
        final String[] aliases_staffchat = commands.STAFFCHAT_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                        .metaBuilder(commands.STAFFCHAT_COMMAND_ALIASES.get(0))
                        .aliases(aliases_staffchat)
                        .build(), new StaffChatCommand(this));

        final String[] aliases_toggle = commands.STAFF_TOGGLE_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.STAFF_TOGGLE_COMMAND_ALIASES.get(0))
                .aliases(aliases_toggle)
                .build(), new ToggleCommand(this));

        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder("staffchatreload")
                .aliases("screload")
                .build(), new ReloadCommand(this));

        final String[] aliases_adminchat = commands.ADMINCHAT_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.ADMINCHAT_COMMAND_ALIASES.get(0))
                .aliases(aliases_adminchat)
                .build(), new AdminChatCommand(this));

        final String[] aliases_adminchat_toggle = commands.ADMIN_TOGGLE_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.ADMIN_TOGGLE_COMMAND_ALIASES.get(0))
                .aliases(aliases_adminchat_toggle)
                .build(), new AdminToggleCommand(this));

        final String[] aliases_devchat = commands.DEVCHAT_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.DEVCHAT_COMMAND_ALIASES.get(0))
                .aliases(aliases_devchat)
                .build(), new DevChatCommand(this));

        final String[] aliases_devchat_toggle = commands.DEV_TOGGLE_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.DEV_TOGGLE_COMMAND_ALIASES.get(0))
                .aliases(aliases_devchat_toggle)
                .build(), new DevToggleCommand(this));

        final String[] aliases_chat = commands.CHAT_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.CHAT_COMMAND_ALIASES.get(0))
                .aliases(aliases_chat)
                .build(), new ChatCommand(this));

        final String[] aliases_hide = commands.STAFF_HIDE_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.STAFF_HIDE_COMMAND_ALIASES.get(0))
                .aliases(aliases_hide)
                .build(), new HideCommand(this));

        final String[] aliases_admin_hide = commands.ADMIN_HIDE_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.CHAT_COMMAND_ALIASES.get(0))
                .aliases(aliases_admin_hide)
                .build(), new AdminHideCommand(this));

        final String[] aliases_dev_hide = commands.DEV_HIDE_COMMAND_ALIASES.toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(commands.DEV_HIDE_COMMAND_ALIASES.get(0))
                .aliases(aliases_dev_hide)
                .build(), new DevHideCommand(this));
        this.color.log("<yellow>Loaded commands.");
    }

    private void loadListeners() {
        server.getEventManager().register(this, new ChatListener(this));
        server.getEventManager().register(this, new JoinListener(this));
        this.color.log("<yellow>Loaded listeners.");
    }

    @SuppressWarnings("ALL")
    public boolean getUnsignedVelocityAddon() {
        return server.getPluginManager().isLoaded("unsignedvelocity");
    }

    public void updateCheck(CommandSource sender, boolean console) {
        try {
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
                JsonObject info = plugins.get(getContainer().getDescription().getName().get()).getAsJsonObject();
                String version = info.get("version").getAsString();
                if (version.equals(getContainer().getDescription().getVersion().get())) {
                    if (console) {
                        this.color.sendMessage(sender, "<green>" + getContainer().getDescription().getName().get() + " is on the latest version.");
                    }
                } else {
                    this.color.sendMessage(sender, " ");
                    this.color.sendMessage(sender, " ");
                    this.color.sendMessage(sender, "<red>Your " + getContainer().getDescription().getName().get() + " version is out of date!");
                    this.color.sendMessage(sender, "<red>We recommend updating ASAP!");
                    this.color.sendMessage(sender, "");
                    this.color.sendMessage(sender, "<red>Your Version: <yellow>" + getContainer().getDescription().getVersion().get());
                    this.color.sendMessage(sender, "<green>Newest Version: <yellow>" + version);
                    this.color.sendMessage(sender, " ");
                    this.color.sendMessage(sender, " ");
                    return;
                }
            } else {
                this.color.sendMessage(sender, "<red>Wrong response from update API, contact plugin developer!");
            }
        } catch (
                Exception ex) {
            this.color.sendMessage(sender, "<red>Failed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}