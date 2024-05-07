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
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
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

    @Override
    public void onEnable() {
        long startTiming = System.currentTimeMillis();
        instance = this;

        new Metrics(this, 12095);

        loadFiles();

        loadModules();

        loadHooks();

        RyMessageUtils.sendConsole(true, "&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        RyMessageUtils.sendConsole(true, "&e" + getDescription().getName() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        RyMessageUtils.sendConsole(true, " &f[*] &6Version&f: &b" + getDescription().getVersion());
        RyMessageUtils.sendConsole(true, " &f[*] &6Name&f: &b" + getDescription().getName());
        RyMessageUtils.sendConsole(true, " &f[*] &6Author&f: &b" + getDescription().getAuthors().get(0));
        RyMessageUtils.sendConsole(true, "&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck();
    }

    private void loadFiles() {
        this.configFile = new ConfigFile("config.yml", true);
        this.commandsFile = new ConfigFile("commands.yml", true);
        this.discordFile = new ConfigFile("discord.yml", true);
        this.localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml", true);

        this.settings = new Config();
        this.commands = new Commands();
        this.discord = new Discord();

        RyMessageUtils.sendConsole(true, "&c==========================================");
        RyMessageUtils.sendConsole(true, "&aAll files have been loaded correctly!");
        RyMessageUtils.sendConsole(true, "&c==========================================");
    }

    private void loadModules() {
        this.commandManager = new CommandManager(this);
        getCommandManager().registerAll();

        if (getSettings().JOIN_ENABLED)
            getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        if (getSettings().CHAT_TOGGLE_ENABLED)
            getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        RyMessageUtils.sendConsole(true, "&aLoaded modules.");
    }

    private void loadHooks() {
        PluginManager pluginManager = getServer().getPluginManager();

        if (!pluginManager.isPluginEnabled("SignedVelocity")) {
            RyMessageUtils.sendConsole(true, "&cIf you get kicked out in 1.19+ while typing in a staffchat on Spigot, " +
                    "consider downloading SignedVelocity: https://modrinth.com/plugin/signedvelocity");
        }

        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            RyMessageUtils.sendConsole(true, "&aHooked into PlaceholderAPI for placeholders.");
        }
    }

    /**
     * @return true if the server is running Paper or a fork of Paper, false otherwise
     */
    public boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
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
                    RyMessageUtils.sendConsole(true, "&cThis plugin has been marked as &e&l'Archived' &cby RefracDevelopment.");
                    RyMessageUtils.sendConsole(true, "&cThis version will continue to work but will not receive updates or support.");
                } else if (version.equals(getDescription().getVersion())) {
                    RyMessageUtils.sendConsole(true, "&a" + getDescription().getName() + " is on the latest version.");
                } else {
                    RyMessageUtils.sendConsole(true, " ");
                    RyMessageUtils.sendConsole(true, "&cYour " + getDescription().getName() + " version &7(" + getDescription().getVersion() + ") &cis out of date! Newest: &e&lv" + version);
                    RyMessageUtils.sendConsole(true, " ");
                }
            } else {
                RyMessageUtils.sendConsole(true, "&cWrong response from update API, contact plugin developer!");
            }
        } catch (Exception ex) {
            RyMessageUtils.sendConsole(true, "&cFailed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}