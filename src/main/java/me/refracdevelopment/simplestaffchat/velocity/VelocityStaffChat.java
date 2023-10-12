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
import me.refracdevelopment.simplestaffchat.velocity.api.VelocityStaffChatAPI;
import me.refracdevelopment.simplestaffchat.velocity.commands.ChatCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.velocity.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;
import me.refracdevelopment.simplestaffchat.velocity.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.velocity.utilities.LuckPermsUtil;
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
        version = "3.2-beta.4",
        dependencies = {@Dependency(id = "unsignedvelocity", optional = true), @Dependency(id = "luckperms", optional = true)},
        url = "https://discord.gg/EFeSKPg739",
        description = "A Simple StaffChat Plugin",
        authors = "RefracDevelopment")
public class VelocityStaffChat {

    @Getter
    private static VelocityStaffChat instance;

    private VelocityStaffChatAPI staffChatAPI;

    private final Metrics.Factory metricsFactory;
    private final ProxyServer server;
    private final Logger logger;
    private final Path path;
    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;

    private DiscordImpl discord;

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
        instance = this;
        long startTiming = System.currentTimeMillis();

        loadFiles();

        if (!getUnsignedVelocityAddon()) {
            Color.log("<red>If you get kicked out in 1.19+ while typing in a staffchat on Velocity, " +
                    "consider downloading https://github.com/4drian3d/UnSignedVelocity/releases/latest");
        }

        if (Config.LUCKPERMS.getBoolean() || server.getPluginManager().isLoaded("luckperms")) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            Color.log("<yellow>Hooked into LuckPerms.");
        }

        loadCommands();
        loadListeners();

        metricsFactory.make(this, 12096);

        this.staffChatAPI = new VelocityStaffChatAPI();
        this.discord = new DiscordImpl();

        Color.log("<gray><strikethrough>==<red><strikethrough>=====<white><strikethrough>======================<red><strikethrough>=====<gray><strikethrough>==");
        Color.log("<yellow>" + getContainer().getDescription().getName().get() + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" <white>[*] <gold>Version&f: <aqua>" + getContainer().getDescription().getVersion().get());
        Color.log(" <white>[*] <gold>Name&f: <aqua>" + getContainer().getDescription().getName().get());
        Color.log(" <white>[*] <gold>Author&f: <aqua>" + getContainer().getDescription().getAuthors().get(0));
        Color.log("<gray><strikethrough>==<red><strikethrough>=====<white><strikethrough>======================<red><strikethrough>=====<gray><strikethrough>==");

        updateCheck(server.getConsoleCommandSource(), true);
    }

    public void loadFiles() {
        this.configFile = new ConfigFile(path, "velocity-config.yml");
        this.commandsFile = new ConfigFile(path, "velocity-commands.yml");
        this.discordFile = new ConfigFile(path, "discord.yml");
        Color.log("<red>==========================================");
        Color.log("<yellow>All files have been loaded correctly!");
        Color.log("<red>==========================================");
    }

    public void reloadFiles() {
        ConfigFile.reloadAll();
        Color.log("<red>==========================================");
        Color.log("<yellow>All files have been loaded correctly!");
        Color.log("<red>==========================================");
    }

    private void loadCommands() {
        final String[] aliases_staffchat = Commands.STAFFCHAT_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                        .metaBuilder(Commands.STAFFCHAT_ALIASES.getStringList().get(0))
                        .aliases(aliases_staffchat)
                        .build(), new StaffChatCommand());

        final String[] aliases_toggle = Commands.TOGGLE_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.TOGGLE_ALIASES.getStringList().get(0))
                .aliases(aliases_toggle)
                .build(), new ToggleCommand());

        final String[] aliases_reload = Commands.RELOAD_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.RELOAD_ALIASES.getStringList().get(0))
                .aliases(aliases_reload)
                .build(), new ReloadCommand(this));

        final String[] aliases_adminchat = Commands.ADMINCHAT_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.ADMINCHAT_ALIASES.getStringList().get(0))
                .aliases(aliases_adminchat)
                .build(), new AdminChatCommand());

        final String[] aliases_adminchat_toggle = Commands.ADMIN_TOGGLE_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.ADMIN_TOGGLE_ALIASES.getStringList().get(0))
                .aliases(aliases_adminchat_toggle)
                .build(), new AdminToggleCommand());

        final String[] aliases_devchat = Commands.DEVCHAT_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.DEVCHAT_ALIASES.getStringList().get(0))
                .aliases(aliases_devchat)
                .build(), new DevChatCommand());

        final String[] aliases_devchat_toggle = Commands.DEV_TOGGLE_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.DEV_TOGGLE_ALIASES.getStringList().get(0))
                .aliases(aliases_devchat_toggle)
                .build(), new DevToggleCommand());

        final String[] aliases_chat = Commands.CHAT_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.CHAT_ALIASES.getStringList().get(0))
                .aliases(aliases_chat)
                .build(), new ChatCommand());
        Color.log("<yellow>Loaded commands.");
    }

    private void loadListeners() {
        server.getEventManager().register(this, new ChatListener());
        server.getEventManager().register(this, new JoinListener());
        Color.log("<yellow>Loaded listeners.");
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
                        Color.sendMessage(sender, "<green>" + getContainer().getDescription().getName().get() + " is on the latest version.");
                    }
                } else {
                    Color.sendMessage(sender, " ");
                    Color.sendMessage(sender, " ");
                    Color.sendMessage(sender, "<red>Your " + getContainer().getDescription().getName().get() + " version is out of date!");
                    Color.sendMessage(sender, "<red>We recommend updating ASAP!");
                    Color.sendMessage(sender, "");
                    Color.sendMessage(sender, "<red>Your Version: <yellow>" + getContainer().getDescription().getVersion().get());
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