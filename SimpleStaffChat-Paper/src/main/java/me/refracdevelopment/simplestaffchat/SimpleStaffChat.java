package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.manager.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.manager.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.manager.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.manager.config.cache.PaperConfig;
import me.refracdevelopment.simplestaffchat.utilities.Metrics;
import me.refracdevelopment.simplestaffchat.utilities.VersionCheck;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public final class SimpleStaffChat extends JavaPlugin {

    @Getter
    private static SimpleStaffChat instance;

    private CommandManager commandManager;
    private ConfigFile configFile;
    private ConfigFile commandsFile;

    private ConfigFile discordFile;
    private ConfigFile localeFile;
    private PaperConfig settings;
    private Commands commands;
    private Discord discord;

    @Override
    public void onEnable() {
        instance = this;

        long startTiming = System.currentTimeMillis();
        PluginManager pluginManager = getServer().getPluginManager();

        Metrics metrics = new Metrics(this, 12095);

        loadFiles();

        if (VersionCheck.isOnePointSeven()) {
            Color.log("&c" + getDescription().getName() + " 1.7 is in legacy mode, please update to 1.8+");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!pluginManager.isPluginEnabled("PlaceholderAPI")) {
            Color.log("&cPlease install PlaceholderAPI onto your server to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!getServer().getPluginManager().isPluginEnabled("SignedVelocity")) {
            Color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on Paper, " +
                    "consider downloading SignedVelocity: https://modrinth.com/plugin/signedvelocity");
        }

        loadModules();

        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Color.log("&e" + getDescription().getName() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" &f[*] &6Version&f: &b" + getDescription().getVersion());
        Color.log(" &f[*] &6Name&f: &b" + getDescription().getName());
        Color.log(" &f[*] &6Author&f: &b" + getDescription().getAuthors().get(0));
        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(getServer().getConsoleSender(), true);
    }

    private void loadFiles() {
        this.configFile = new ConfigFile("config.yml");
        this.commandsFile = new ConfigFile("commands.yml");
        this.discordFile = new ConfigFile("discord.yml");
        this.localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml");

        this.settings = new PaperConfig();
        this.commands = new Commands();
        this.discord = new Discord();

        Color.log("&c==========================================");
        Color.log("&aAll files have been loaded correctly!");
        Color.log("&c==========================================");
    }

    private void loadModules() {
        this.commandManager = new CommandManager(this);
        getCommandManager().registerAll();

        if (getSettings().JOIN_ENABLED)
            getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        if (getSettings().CHAT_TOGGLE_ENABLED)
            getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        Color.log("&aLoaded modules.");
    }

    public void updateCheck(CommandSender sender, boolean console) {
        try {
            String urlString = "https://refracdev-updatecheck.refracdev.workers.dev/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String input;

            while ((input = reader.readLine()) != null) {
                response.append(input);
            }

            reader.close();
            JsonObject object = new JsonParser().parse(response.toString()).getAsJsonObject();

            if (object.has("plugins")) {
                JsonObject plugins = object.get("plugins").getAsJsonObject();
                JsonObject info = plugins.get(getDescription().getName()).getAsJsonObject();
                String version = info.get("version").getAsString();
                boolean archived = info.get("archived").getAsBoolean();

                if (archived) {
                    sender.sendMessage(Color.translate("&cThis plugin has been marked as &e&l'Archived' &cby RefracDevelopment."));
                    sender.sendMessage(Color.translate("&cThis version will continue to work but will not receive updates or support."));
                } else if (version.equals(getDescription().getVersion())) {
                    if (console) {
                        sender.sendMessage(Color.translate("&a" + getDescription().getName() + " is on the latest version."));
                    }
                } else {
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour " + getDescription().getName() + " version &7(" + getDescription().getVersion() + ") &cis out of date! Newest: &e&lv" + version));
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