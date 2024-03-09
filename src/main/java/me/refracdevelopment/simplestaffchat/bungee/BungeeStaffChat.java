package me.refracdevelopment.simplestaffchat.bungee;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.bungee.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.bungee.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.bungee.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.LuckPermsUtil;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public class BungeeStaffChat extends Plugin {

    @Getter private static BungeeStaffChat instance;

    // Managers
    private CommandManager commandManager;

    // Files
    private ConfigFile configFile;
    private ConfigFile commandsFile;
    private ConfigFile discordFile;
    private ConfigFile localeFile;

    // Caches
    private Config config;
    private Commands commands;
    private Discord discord;

    @Override
    public void onEnable() {
        instance = this;
        long startTiming = System.currentTimeMillis();

        loadFiles();

        new Metrics(this, 12096);

        if (getConfig().LUCKPERMS || getProxy().getPluginManager().getPlugin("LuckPerms") != null) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            Color.log("&aHooked into LuckPerms.");
        }

        if (!getAntiPopupAddon() && getViaVersion()) {
            Color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on BungeeCord, " +
                    "consider downloading https://www.spigotmc.org/resources/%E2%9C%A8-antipopup-bungeecord-viaversion-addon-%E2%9C%A8.104628/");
        }

        loadCommands();
        loadListeners();

        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Color.log("&e" + getDescription().getName() + " has been enabled. (took " + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" &f[*] &6Version&f: &b" + getDescription().getVersion());
        Color.log(" &f[*] &6Name&f: &b" + getDescription().getName());
        Color.log(" &f[*] &6Author&f: &b" + getDescription().getAuthor());
        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(getProxy().getConsole(), true);
    }

    private void loadFiles() {
        // Files
        this.configFile = new ConfigFile("bungee-config.yml");
        this.commandsFile = new ConfigFile("commands.yml");
        this.discordFile = new ConfigFile("discord.yml");
        this.localeFile = new ConfigFile("locale/" + this.configFile.getConfigFile().getString("locale") + ".yml");

        // Caches
        this.config = new Config();
        this.commands = new Commands();
        this.discord = new Discord();

        Color.log("&c==========================================");
        Color.log("&eAll files have been loaded correctly!");
        Color.log("&c==========================================");
    }

    private void loadCommands() {
        this.commandManager = new CommandManager(this);
        getCommandManager().registerAll();
        Color.log("&aLoaded commands.");
    }

    private void loadListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinListener(this));
        getProxy().getPluginManager().registerListener(this, new ChatListener(this));
        Color.log("&aLoaded listeners.");
    }

    public boolean getAntiPopupAddon() {
        return getProxy().getPluginManager().getPlugin("AntiPopup-Via") != null;
    }

    public boolean getViaVersion() {
        return getProxy().getPluginManager().getPlugin("ViaVersion") != null;
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
            StringBuilder response = new StringBuilder();
            while ((input = reader.readLine()) != null) {
                response.append(input);
            }
            reader.close();
            JsonObject object = JsonParser.parseString(response.toString()).getAsJsonObject();

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
        } catch (
                Exception ex) {
            sender.sendMessage(Color.translate("&cFailed to get updater check. (" + ex.getMessage() + ")"));
        }
    }
}