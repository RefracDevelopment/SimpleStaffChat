package me.refracdevelopment.simplestaffchat.bungee;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.api.BungeeStaffChatAPI;
import me.refracdevelopment.simplestaffchat.bungee.config.YMLBase;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.bungee.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.bungee.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.bungee.manager.CommandManager;
import me.refracdevelopment.simplestaffchat.bungee.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.bungee.utilities.LuckPermsUtil;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
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

    private CommandManager commandManager;

    private BungeeStaffChatAPI staffChatAPI;

    private YMLBase configFile;
    private YMLBase commandsFile;
    private YMLBase discordFile;

    private Config config;
    private Commands commands;
    private Discord discord;

    private DiscordImpl discordImpl;
    private Methods methods;
    private Permissions permissions;
    private Color color;
    private Placeholders placeholders;
    private LuckPermsUtil luckPermsUtil;

    @Override
    public void onEnable() {
        long startTiming = System.currentTimeMillis();
        
        this.color = new Color(this);
        this.placeholders = new Placeholders(this);
        this.permissions = new Permissions();

        loadFiles();

        loadCommands();
        loadListeners();

        if (this.config.LUCKPERMS || getProxy().getPluginManager().getPlugin("LuckPerms") != null) {
            this.luckPermsUtil = new LuckPermsUtil(this);
            this.luckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            this.color.log("&eHooked into LuckPerms.");
        }

        if (!getAntiPopupAddon() && getViaVersion()) {
            this.color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on BungeeCord, " +
                    "consider downloading https://www.spigotmc.org/resources/%E2%9C%A8-antipopup-bungeecord-viaversion-addon-%E2%9C%A8.104628/");
        }

        new Metrics(this, 12096);

        this.staffChatAPI = new BungeeStaffChatAPI(this);
        this.discordImpl = new DiscordImpl(this);
        this.methods = new Methods(this);

        this.color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        this.color.log("&e" + getDescription().getName() + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        this.color.log(" &f[*] &6Version&f: &b" + getDescription().getVersion());
        this.color.log(" &f[*] &6Name&f: &b" + getDescription().getName());
        this.color.log(" &f[*] &6Author&f: &b" + getDescription().getAuthor());
        this.color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(getProxy().getConsole(), true);
    }

    private void loadFiles() {
        this.configFile = new YMLBase(this, "bungee-config.yml");
        this.commandsFile = new YMLBase(this, "commands.yml");
        this.discordFile = new YMLBase(this, "discord.yml");
        this.config = new Config(this);
        this.commands = new Commands(this);
        this.discord = new Discord(this);
        this.config.loadConfig();
        this.commands.loadConfig();
        this.discord.loadConfig();
        this.color.log("&c==========================================");
        this.color.log("&eAll files have been loaded correctly!");
        this.color.log("&c==========================================");
    }

    public void reloadFiles() {
        this.configFile.load();
        this.commandsFile.load();
        this.discordFile.load();
        this.config.loadConfig();
        this.commands.loadConfig();
        this.discord.loadConfig();
        this.color.log("&c==========================================");
        this.color.log("&eAll files have been loaded correctly!");
        this.color.log("&c==========================================");
    }

    private void loadCommands() {
        this.commandManager = new CommandManager(this);
        commandManager.registerAll();
        this.color.log("&eLoaded commands.");
    }

    private void loadListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinListener(this));
        getProxy().getPluginManager().registerListener(this, new ChatListener(this));
        this.color.log("&eLoaded listeners.");
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
}