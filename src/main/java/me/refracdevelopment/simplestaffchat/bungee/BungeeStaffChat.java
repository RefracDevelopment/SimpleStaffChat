package me.refracdevelopment.simplestaffchat.bungee;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.commands.ChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.config.YMLBase;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.bungee.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.bungee.utilities.LuckPermsUtil;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import org.bstats.bungeecord.Metrics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public class BungeeStaffChat extends Plugin {

    @Getter
    private static BungeeStaffChat instance;
    private YMLBase configFile;
    private YMLBase commandsFile;

    @Override
    public void onEnable() {
        instance = this;
        long startTiming = System.currentTimeMillis();

        loadFiles();

        loadCommands();
        loadListeners();

        if (Config.LUCKPERMS || getProxy().getPluginManager().getPlugin("LuckPerms") != null) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            Color.log("&eHooked into LuckPerms.");
        }

        if (!getAntiPopupAddon() && getViaVersion()) {
            Color.log("&cIf you get kicked out in 1.19+ while typing in a staffchat on BungeeCord, " +
                    "consider downloading https://www.spigotmc.org/resources/%E2%9C%A8-antipopup-bungeecord-viaversion-addon-%E2%9C%A8.104628/");
        }

        new Metrics(this, 12096);

        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Color.log("&e" + getDescription().getName() + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" &f[*] &6Version&f: &b" + getDescription().getVersion());
        Color.log(" &f[*] &6Name&f: &b" + getDescription().getName());
        Color.log(" &f[*] &6Author&f: &b" + getDescription().getAuthor());
        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(getProxy().getConsole(), true);
    }

    private void loadFiles() {
        this.configFile = new YMLBase(this, "bungee-config.yml");
        this.commandsFile = new YMLBase(this, "commands.yml");
        Config.loadConfig();
        Commands.loadConfig();
        Color.log("&c==========================================");
        Color.log("&eAll files have been loaded correctly!");
        Color.log("&c==========================================");
    }

    private void loadCommands() {
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerCommand(this, new StaffChatCommand(this));
        pluginManager.registerCommand(this, new ToggleCommand());
        pluginManager.registerCommand(this, new ReloadCommand(this));
        pluginManager.registerCommand(this, new AdminChatCommand(this));
        pluginManager.registerCommand(this, new AdminToggleCommand());
        pluginManager.registerCommand(this, new DevChatCommand(this));
        pluginManager.registerCommand(this, new DevToggleCommand());
        pluginManager.registerCommand(this, new ChatCommand());
        Color.log("&eLoaded commands.");
    }

    private void loadListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinListener());
        getProxy().getPluginManager().registerListener(this, new ChatListener());
        Color.log("&eLoaded listeners.");
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