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
import me.refracdevelopment.simplestaffchat.velocity.utilities.LuckPermsUtil;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPermsProvider;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

@Getter
@Plugin(id = "simplestaffchat2",
        name = "SimpleStaffChat2",
        version = "3.1",
        dependencies = {@Dependency(id = "unsignedvelocity", optional = true), @Dependency(id = "luckperms", optional = true)},
        url = "https://discord.refracdev.ml",
        description = "A Simple StaffChat Plugin",
        authors = "RefracDevelopment")
public class VelocityStaffChat {

    @Getter
    private static VelocityStaffChat instance;
    private final Metrics.Factory metricsFactory;
    private final ProxyServer server;
    private final Logger logger;
    private final Path path;
    private ConfigFile configFile;
    private ConfigFile commandsFile;

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

        loadCommands();
        loadListeners();

        Color.log(Level.WARN, "§ePlease note that this is an experimental build for" +
                "Velocity support some things will not work.");

        if (Config.LUCKPERMS.getBoolean() || server.getPluginManager().isLoaded("luckperms")) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            Color.log(Level.INFO, "§eHooked into LuckPerms.");
        }

        if (!getUnsignedVelocityAddon()) {
            Color.log(Level.WARN, "§cIf you get kicked out in 1.19+ while typing in a staffchat on Velocity, " +
                    "consider downloading https://github.com/4drian3d/UnSignedVelocity/releases/latest");
        }

        metricsFactory.make(this, 15921);
        
        Color.log(Level.INFO, "§8§m==§c§m=====§f§m======================§c§m=====§8§m==");
        Color.log(Level.INFO, "§e" + getContainer().getDescription().getName().get() + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(Level.INFO, " §f[*] §6Version§f: §b" + getContainer().getDescription().getVersion().get());
        Color.log(Level.INFO, " §f[*] §6Name§f: §b" + getContainer().getDescription().getName().get());
        Color.log(Level.INFO, " §f[*] §6Author§f: §b" + getContainer().getDescription().getAuthors().get(0));
        Color.log(Level.INFO, "§8§m==§c§m=====§f§m======================§c§m=====§8§m==");

        updateCheck(server.getConsoleCommandSource(), true);
    }

    public void loadFiles() {
        this.configFile = new ConfigFile(path, "bungee-config.yml");
        this.commandsFile = new ConfigFile(path, "velocity-commands.yml");
        Color.log(Level.INFO, "§c==========================================");
        Color.log(Level.INFO, "§eAll files have been loaded correctly!");
        Color.log(Level.INFO, "§c==========================================");
    }

    private void loadCommands() {
        final String[] aliases_staffchat = Commands.STAFFCHAT_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                        .metaBuilder(Commands.STAFFCHAT_ALIASES.getStringList().get(0))
                        .aliases(aliases_staffchat)
                        .build(), new StaffChatCommand(this));

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
                .build(), new AdminChatCommand(this));

        final String[] aliases_adminchat_toggle = Commands.ADMIN_TOGGLE_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.ADMIN_TOGGLE_ALIASES.getStringList().get(0))
                .aliases(aliases_adminchat_toggle)
                .build(), new AdminToggleCommand());

        final String[] aliases_devchat = Commands.DEVCHAT_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.DEVCHAT_ALIASES.getStringList().get(0))
                .aliases(aliases_devchat)
                .build(), new DevChatCommand(this));

        final String[] aliases_devchat_toggle = Commands.DEV_TOGGLE_ALIASES.getStringList().toArray(new String[0]);
        server.getCommandManager().register(server.getCommandManager()
                .metaBuilder(Commands.DEV_TOGGLE_ALIASES.getStringList().get(0))
                .aliases(aliases_devchat_toggle)
                .build(), new DevToggleCommand());
        Color.log(Level.INFO, "§eLoaded commands.");
    }

    private void loadListeners() {
        server.getEventManager().register(this, new ChatListener());
        server.getEventManager().register(this, new JoinListener());
        Color.log(Level.INFO, "§eLoaded listeners.");
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
                        sender.sendMessage(Component.text(Color.translate("§a" + getContainer().getDescription().getName().get() + " is on the latest version.")));
                    }
                } else {
                    sender.sendMessage(Component.text(""));
                    sender.sendMessage(Component.text(""));
                    sender.sendMessage(Component.text("§cYour " + getContainer().getDescription().getName().get() + " version is out of date!"));
                    sender.sendMessage(Component.text("§cWe recommend updating ASAP!"));
                    sender.sendMessage(Component.text(""));
                    sender.sendMessage(Component.text("§cYour Version: §e" + getContainer().getDescription().getVersion().get()));
                    sender.sendMessage(Component.text("§aNewest Version: §e" + version));
                    sender.sendMessage(Component.text(""));
                    sender.sendMessage(Component.text(""));
                    return;
                }
            } else {
                sender.sendMessage(Component.text("§cWrong response from update API, contact plugin developer!"));
            }
        } catch (
                Exception ex) {
            sender.sendMessage(Component.text("§cFailed to get updater check. (" + ex.getMessage() + ")"));
        }
    }
}