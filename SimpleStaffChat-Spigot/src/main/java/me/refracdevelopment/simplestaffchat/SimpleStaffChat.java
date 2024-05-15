package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.lib.PaperLib;
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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import space.arim.morepaperlib.MorePaperLib;

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

    private MorePaperLib paperLib;

    @Override
    public void onEnable() {
        instance = this;

        new Metrics(this, 12095);

        paperLib = new MorePaperLib(SimpleStaffChat.getInstance());

        loadFiles();

        RyMessageUtils.sendConsole(false,
                "<#A020F0> _____ _           _     _____ _       ___ ___ _____ _       _     " + "Running <#7D0DC3>v" + getDescription().getVersion(),
                "<#A020F0>|   __|_|_____ ___| |___|   __| |_  __|  _|  _|     | |_  __| |_   " + "Server <#7D0DC3>" + getServer().getName() + " <#A020F0>v" + getServer().getVersion(),
                "<#A020F0>|__   | |     | . | | -_|__   |  _||. |  _|  _|   --|   ||. |  _|  " + "Discord support: <#7D0DC3>" + getDescription().getWebsite(),
                "<#7D0DC3>|_____|_|_|_|_|  _|_|___|_____| | |___|_| |_| |_____|_|_|___| |    " + "Thanks for using my plugin ❤ !",
                "<#7D0DC3>              |_|             |__|                          |__|",
                "        <#A020F0>Developed by <#7D0DC3>RefracDevelopment",
                ""
        );

        loadModules();
        loadHooks();

        // Paper is recommended but not required
        PaperLib.suggestPaper(this);

        paperLib.scheduling().asyncScheduler().run(this::updateCheck);
    }

    private void loadFiles() {
        this.configFile = new ConfigFile("config.yml");
        this.commandsFile = new ConfigFile("commands.yml");
        this.discordFile = new ConfigFile("discord.yml");
        this.localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml");

        this.settings = new Config();
        this.commands = new Commands();
        this.discord = new Discord();

        RyMessageUtils.sendConsole(true, "&aLoaded all files.");
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

        if (!pluginManager.isPluginEnabled("AntiPopup")) {
            RyMessageUtils.sendConsole(true, "&cIf you get kicked out in 1.19+ while typing in a staffchat on Spigot",
                    "&cconsider downloading AntiPopup: https://www.spigotmc.org/resources/103782/");
        }

        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            RyMessageUtils.sendConsole(true, "&aHooked into PlaceholderAPI for placeholders.");
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
                    RyMessageUtils.sendConsole(true, "&cYour " + getDescription().getName() + " version &7(" + getDescription().getVersion() + ") &cis out of date! Newest: &e&lv" + version);
                }
            } else {
                RyMessageUtils.sendConsole(true, "&cWrong response from update API, contact plugin developer!");
            }
        } catch (Exception ex) {
            RyMessageUtils.sendConsole(true, "&cFailed to get updater check. (" + ex.getMessage() + ")");
        }
    }
}