package me.refracdevelopment.simplestaffchat.spigot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.spigot.api.SimpleStaffChatAPI;
import me.refracdevelopment.simplestaffchat.spigot.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Config;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.spigot.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.PluginMessage;
import me.refracdevelopment.simplestaffchat.spigot.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.VersionCheck;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public final class SimpleStaffChat extends JavaPlugin {

    @Getter private static SimpleStaffChat instance;

    // Managers
    private CommandManager commandManager;
    private PluginMessage pluginMessage;

    // Files
    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;
    private ConfigFile localeFile;

    // Caches
    private Config settings;
    private Commands commands;
    private Discord discord;

    private SimpleStaffChatAPI staffChatAPI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        long startTiming = System.currentTimeMillis();
        PluginManager pluginManager = getServer().getPluginManager();

        loadFiles();

        new Metrics(this, 12095);

        // Check if the server is on 1.7
        if (VersionCheck.isOnePointSeven()) {
            Color.log("&c" + getDescription().getName() + " 1.7 is in legacy mode, please update to 1.8+");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Make sure the server has PlaceholderAPI
        if (pluginManager.getPlugin("PlaceholderAPI") == null && !isFolia()) {
            Color.log("&cPlease install PlaceholderAPI onto your server to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (isFolia()) {
            Color.log("&cSupport for Folia has not been tested and is only for experimental purposes.");
        }

        if (!getAntiPopupAddon()) {
            Color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on Spigot, " +
                    "consider downloading AntiPopup: https://www.spigotmc.org/resources/103782/");
        }

        if (this.settings.BUNGEECORD) {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this.pluginMessage = new PluginMessage(this));
        }

        loadCommands();
        loadListeners();

        this.staffChatAPI = new SimpleStaffChatAPI();

        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Color.log("&e" + getDescription().getName() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" &f[*] &6Version&f: &b" + getDescription().getVersion());
        Color.log(" &f[*] &6Name&f: &b" + getDescription().getName());
        Color.log(" &f[*] &6Author&f: &b" + getDescription().getAuthors().get(0));
        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(getServer().getConsoleSender(), true);
    }

    @Override
    public void onDisable() {
        if (this.settings.BUNGEECORD) {
            getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
            getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", pluginMessage);
        }
    }

    private void loadFiles() {
        // Files
        this.configFile = new ConfigFile("config.yml");
        this.commandsFile = new ConfigFile("commands.yml");
        this.discordFile = new ConfigFile("discord.yml");
        this.localeFile = new ConfigFile("locale/" + configFile.getString("locale") + ".yml");

        // Caches
        this.settings = new Config();
        this.commands = new Commands();
        this.discord = new Discord();
    }

    public void reloadFiles() {
        // Files
        this.configFile.reload();
        this.commandsFile.reload();
        this.discordFile.reload();
        this.localeFile.reload();

        // Caches
        this.settings.loadConfig();
        this.commands.loadConfig();
        this.discord.loadConfig();

        Color.log("&c==========================================");
        Color.log("&eAll files have been reloaded correctly!");
        Color.log("&c==========================================");
    }

    private void loadCommands() {
        this.commandManager = new CommandManager(this);
        commandManager.registerAll();
        Color.log("&aLoaded commands.");
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        Color.log("&aLoaded listeners.");
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
                        sender.sendMessage(Color.translate("&a" + getDescription().getName() + " is on the latest version."));
                    }
                } else {
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour " + getDescription().getName() + " version is out of date!"));
                    sender.sendMessage(Color.translate("&cWe recommend updating ASAP!"));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour Version: &e" + getDescription().getVersion()));
                    sender.sendMessage(Color.translate("&aNewest Version: &e" + version));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    return;
                }
            } else {
                sender.sendMessage(Color.translate("&cWrong response from update API, contact plugin developer!"));
            }
        } catch (
                Exception ex) {
            sender.sendMessage(Color.translate("&cFailed to get updater check. (" + ex.getMessage() + ")"));
        }
    }
}