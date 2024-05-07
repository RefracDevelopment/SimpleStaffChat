package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.config.cache.Config;
import me.refracdevelopment.simplestaffchat.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.config.cache.Servers;
import me.refracdevelopment.simplestaffchat.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public final class SimpleStaffChat extends Plugin {

    @Getter
    private static SimpleStaffChat instance;

    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;
    private ConfigFile localeFile;
    private ConfigFile serversFile;

    private Config config;
    private Commands commands;
    private Discord discord;
    private Servers servers;

    @Override
    public void onEnable() {
        // Plugin startup logic
        long startTiming = System.currentTimeMillis();
        instance = this;

        new Metrics(this, 12096);

        loadFiles();

        loadModules();

        RyMessageUtils.sendConsole(true, "&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        RyMessageUtils.sendConsole(true, "&e" + getDescription().getName() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        RyMessageUtils.sendConsole(true, " &f[*] &6Version&f: &b" + getDescription().getVersion());
        RyMessageUtils.sendConsole(true, " &f[*] &6Name&f: &b" + getDescription().getName());
        RyMessageUtils.sendConsole(true, " &f[*] &6Author&f: &b" + getDescription().getAuthor());
        RyMessageUtils.sendConsole(true, "&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck();
    }

    private void loadFiles() {
        this.configFile = new ConfigFile("config.yml", true);
        this.commandsFile = new ConfigFile("commands.yml", true);
        this.discordFile = new ConfigFile("discord.yml", true);
        this.localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml", true);
        this.serversFile = new ConfigFile("servers.yml", true);

        this.config = new Config();
        this.commands = new Commands();
        this.discord = new Discord();
        this.servers = new Servers();

        RyMessageUtils.sendConsole(true, "&c==========================================");
        RyMessageUtils.sendConsole(true, "&aAll files have been loaded correctly!");
        RyMessageUtils.sendConsole(true, "&c==========================================");
    }

    private void loadModules() {


        if (getConfig().JOIN_ENABLED)
            getProxy().getPluginManager().registerListener(this, new JoinListener(this));
        if (getConfig().CHAT_TOGGLE_ENABLED)
            getProxy().getPluginManager().registerListener(this, new ChatListener(this));

        RyMessageUtils.sendConsole(true, "&aLoaded modules.");
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
                    RyMessageUtils.sendConsole(true, "");
                    RyMessageUtils.sendConsole(true, "&cYour " + getDescription().getName() + " version &7(" + getDescription().getVersion() + ") &cis out of date! Newest: &e&lv" + version);
                    RyMessageUtils.sendConsole(true, "");
                }
            } else {
                RyMessageUtils.sendConsole(true, "&cWrong response from update API, contact plugin developer!");
            }
        } catch (Exception ex) {
            RyMessageUtils.sendConsole(true, "&cFailed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}
