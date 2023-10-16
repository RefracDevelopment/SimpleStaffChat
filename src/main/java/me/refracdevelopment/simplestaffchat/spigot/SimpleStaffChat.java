package me.refracdevelopment.simplestaffchat.spigot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.manager.Manager;
import dev.rosewood.rosegarden.utils.NMSUtil;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.api.SimpleStaffChatAPI;
import me.refracdevelopment.simplestaffchat.spigot.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Config;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.spigot.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.PluginMessage;
import me.refracdevelopment.simplestaffchat.spigot.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.spigot.manager.ConfigurationManager;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Getter
public final class SimpleStaffChat extends RosePlugin {

    private CommandManager commandManager;

    private SimpleStaffChatAPI staffChatAPI;

    private PluginMessage pluginMessage;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;

    @Getter
    private Config settings;
    private Commands commands;
    private Discord discord;

    private DiscordImpl discordImpl;
    private Methods methods;
    private Permissions permissions;
    private Color color;
    private Placeholders placeholders;

    public SimpleStaffChat() {
        super(-1, 12095, ConfigurationManager.class, null, LocaleManager.class, null);
    }

    @Override
    protected void enable() {
        // Plugin startup logic
        long startTiming = System.currentTimeMillis();
        PluginManager pluginManager = getServer().getPluginManager();

        this.permissions = new Permissions();
        this.color = new Color(this);
        this.placeholders = new Placeholders(this);

        loadFiles();

        // Check if the server is on 1.7
        if (NMSUtil.getVersionNumber() <= 7) {
            this.color.log("&cSimpleStaffChat2 1.7 is in legacy mode, please update to 1.8+");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Make sure the server has PlaceholderAPI
        if (pluginManager.getPlugin("PlaceholderAPI") == null && !isFolia()) {
            this.color.log("&cPlease install PlaceholderAPI onto your server to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (isFolia()) {
            this.color.log("&cSupport for Folia has not been tested and is only for experimental purposes.");
        }

        this.discordImpl = new DiscordImpl(this);
        this.methods = new Methods(this);

        if (this.settings.BUNGEECORD) {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", pluginMessage = new PluginMessage(this));
        }

        loadCommands();
        loadListeners();

        if (!getAntiPopupAddon()) {
            this.color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on Spigot, " +
                    "consider downloading https://github.com/KaspianDev/AntiPopup/releases/latest");
        }

        this.staffChatAPI = new SimpleStaffChatAPI(this);

        this.color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        this.color.log("&e" + getDescription().getName() + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        this.color.log(" &f[*] &6Version&f: &b" + getDescription().getVersion());
        this.color.log(" &f[*] &6Name&f: &b" + getDescription().getName());
        this.color.log(" &f[*] &6Author&f: &b" + getDescription().getAuthors().get(0));
        this.color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(Bukkit.getConsoleSender(), true);
    }

    @Override
    protected void disable() {
        if (this.settings.BUNGEECORD) {
            getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
            getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", pluginMessage);
        }
    }

    @Override
    protected List<Class<? extends Manager>> getManagerLoadPriority() {
        return Collections.emptyList();
    }

    private void loadFiles() {
        this.commandsFile = new ConfigFile(this, "commands.yml");
        this.discordFile = new ConfigFile(this, "discord.yml");
        this.settings = new Config(this);
        this.commands = new Commands(this);
        this.discord = new Discord(this);
        this.settings.loadConfig();
        this.commands.loadConfig();
        this.discord.loadConfig();
    }

    private void loadCommands() {
        this.commandManager = new CommandManager(this);
        commandManager.registerAll();
        this.color.log("&aLoaded commands.");
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        this.color.log("&aLoaded listeners.");
    }

    public boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServerInitEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @SuppressWarnings("ALL")
    public boolean getAntiPopupAddon() {
        return getServer().getPluginManager().isPluginEnabled("AntiPopup");
    }

    public void updateCheck(CommandSender sender, boolean console) {
        this.color.log("&aChecking for updates!");
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
                JsonObject info = plugins.get(getDescription().getName()).getAsJsonObject();
                String version = info.get("version").getAsString();
                if (version.equals(getDescription().getVersion())) {
                    if (console) {
                        sender.sendMessage(this.color.translate("&a" + getDescription().getName() + " is on the latest version."));
                    }
                } else {
                    sender.sendMessage(this.color.translate(""));
                    sender.sendMessage(this.color.translate(""));
                    sender.sendMessage(this.color.translate("&cYour " + getDescription().getName() + " version is out of date!"));
                    sender.sendMessage(this.color.translate("&cWe recommend updating ASAP!"));
                    sender.sendMessage(this.color.translate(""));
                    sender.sendMessage(this.color.translate("&cYour Version: &e" + getDescription().getVersion()));
                    sender.sendMessage(this.color.translate("&aNewest Version: &e" + version));
                    sender.sendMessage(this.color.translate(""));
                    sender.sendMessage(this.color.translate(""));
                    return;
                }
            } else {
                sender.sendMessage(this.color.translate("&cWrong response from update API, contact plugin developer!"));
            }
        } catch (
                Exception ex) {
            sender.sendMessage(this.color.translate("&cFailed to get updater check. (" + ex.getMessage() + ")"));
        }
    }

    public void reloadFiles() {
        this.commandsFile.load();
        this.discordFile.load();
        this.settings.loadConfig();
        this.commands.loadConfig();
        this.discord.loadConfig();
    }
}