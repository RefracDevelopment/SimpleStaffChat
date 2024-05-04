package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.manager.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.manager.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.manager.config.cache.Config;
import me.refracdevelopment.simplestaffchat.manager.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.utilities.Metrics;
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
    private Config settings;
    private Commands commands;
    private Discord discord;

    private boolean usingPaper = false;

    @Override
    public void onEnable() {
        instance = this;

        // This is only used to detect for things like MiniMessage
        // So that it will work properly on Spigot 1.8+ and Paper 1.8/1.18+
        try {
            Class.forName("io.papermc.paper.configuration.Configuration");
            usingPaper = true;
        } catch (Exception ignored) {
        }

        new Metrics(this, 12095);

        long startTiming = System.currentTimeMillis();
        PluginManager pluginManager = getServer().getPluginManager();

        loadFiles();

        if (!getServer().getPluginManager().isPluginEnabled("SignedVelocity")) {
            Color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on Spigot, " +
                    "consider downloading SignedVelocity: https://modrinth.com/plugin/signedvelocity");
        }

        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            Color.log("&aHooked into PlaceholderAPI for placeholders.");
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
        this.configFile = new ConfigFile("config.yml", true);
        this.commandsFile = new ConfigFile("commands.yml", true);
        this.discordFile = new ConfigFile("discord.yml", true);
        this.localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml", true);

        this.settings = new Config();
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
                    Color.sendCustomMessage(sender, "&cThis plugin has been marked as &e&l'Archived' &cby RefracDevelopment.");
                    Color.sendCustomMessage(sender, "&cThis version will continue to work but will not receive updates or support.");
                } else if (version.equals(getDescription().getVersion())) {
                    if (console) {
                        Color.sendCustomMessage(sender, "&a" + getDescription().getName() + " is on the latest version.");
                    }
                } else {
                    sender.sendMessage("");
                    Color.sendCustomMessage(sender, "&cYour " + getDescription().getName() + " version &7(" + getDescription().getVersion() + ") &cis out of date! Newest: &e&lv" + version);
                    sender.sendMessage("");
                }
            } else {
                Color.sendCustomMessage(sender, "&cWrong response from update API, contact plugin developer!");
            }
        } catch (Exception ex) {
            Color.sendCustomMessage(sender, "&cFailed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}