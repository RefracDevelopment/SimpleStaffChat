package me.refracdevelopment.simplestaffchat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.commands.*;
import me.refracdevelopment.simplestaffchat.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.config.cache.Config;
import me.refracdevelopment.simplestaffchat.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.config.cache.Servers;
import me.refracdevelopment.simplestaffchat.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.utilities.chat.LuckPermsUtil;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.luckperms.api.LuckPermsProvider;
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
        instance = this;

        new Metrics(this, 12096);

        RyMessageUtils.sendConsole(false,
                "<#A020F0> _____ _           _     _____ _       ___ ___ _____ _       _     " + "Running <#7D0DC3>v" + getDescription().getVersion(),
                "<#A020F0>|   __|_|_____ ___| |___|   __| |_  __|  _|  _|     | |_  __| |_   " + "Server <#7D0DC3>BungeeCord <#A020F0>v" + getProxy().getVersion(),
                "<#A020F0>|__   | |     | . | | -_|__   |  _||. |  _|  _|   --|   ||. |  _|  " + "Discord support: <#7D0DC3>https://discord.gg/EFeSKPg739",
                "<#7D0DC3>|_____|_|_|_|_|  _|_|___|_____| | |___|_| |_| |_____|_|_|___| |    " + "Thanks for using my plugin ❤ !",
                "<#7D0DC3>              |_|             |__|                          |__|",
                "     <#A020F0>Developed by <#7D0DC3>RefracDevelopment",
                ""
        );

        loadFiles();
        loadModules();
        loadHooks();

        updateCheck();
    }

    private void loadFiles() {
        this.configFile = new ConfigFile("config.yml");
        this.commandsFile = new ConfigFile("commands.yml");
        this.discordFile = new ConfigFile("discord.yml");
        this.localeFile = new ConfigFile("locale/" + getConfigFile().getString("locale") + ".yml");
        this.serversFile = new ConfigFile("servers.yml");

        this.config = new Config();
        this.commands = new Commands();
        this.discord = new Discord();
        this.servers = new Servers();

        RyMessageUtils.sendConsole(true, "&aLoaded all files.");
    }

    private void loadModules() {
        if (getConfig().STAFFCHAT_ENABLED)
            getProxy().getPluginManager().registerCommand(this, new StaffChatCommand(this));

        if (getConfig().ADMINCHAT_ENABLED)
            getProxy().getPluginManager().registerCommand(this, new AdminChatCommand(this));

        if (getConfig().DEVCHAT_ENABLED)
            getProxy().getPluginManager().registerCommand(this, new DevChatCommand(this));

        if (getConfig().JOIN_ENABLED)
            getProxy().getPluginManager().registerListener(this, new JoinListener(this));

        if (getConfig().CHAT_TOGGLE_ENABLED) {
            getProxy().getPluginManager().registerListener(this, new ChatListener(this));
            getProxy().getPluginManager().registerCommand(this, new ToggleCommand(this));
            getProxy().getPluginManager().registerCommand(this, new AdminToggleCommand(this));
            getProxy().getPluginManager().registerCommand(this, new DevToggleCommand(this));
            getProxy().getPluginManager().registerCommand(this, new HideCommand(this));
            getProxy().getPluginManager().registerCommand(this, new ChatCommand(this));
        }

        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));

        RyMessageUtils.sendConsole(true, "&aLoaded modules.");
    }

    private void loadHooks() {
        if (getProxy().getPluginManager().getPlugin("LuckPerms") != null) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            RyMessageUtils.sendConsole(true, "&aHooked into LuckPerms.");
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
