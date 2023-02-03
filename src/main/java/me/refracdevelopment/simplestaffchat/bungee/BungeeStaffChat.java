package me.refracdevelopment.simplestaffchat.bungee;

import co.aikar.commands.BungeeCommandIssuer;
import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.ConditionFailedException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.config.Config;
import me.refracdevelopment.simplestaffchat.bungee.config.YMLBase;
import me.refracdevelopment.simplestaffchat.bungee.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.bungee.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.bungee.utilities.LuckPermsUtil;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Settings;
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

    @Getter
    private static BungeeStaffChat instance;
    private YMLBase configFile;

    @Override
    public void onEnable() {
        instance = this;
        long startTiming = System.currentTimeMillis();

        this.configFile = new YMLBase(this, "config.yml");
        Config.setConfig(this.configFile);
        Config.load();

        loadCommands();
        loadListeners();

        if (Config.LUCKPERMS.toBoolean()) {
            LuckPermsUtil.setLuckPerms(LuckPermsProvider.get());
            Color.log("&aHooked into LuckPerms.");
        }

        new Metrics(this, 12096);

        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Color.log("&e" + Settings.getName + " has been enabled. (" + (System.currentTimeMillis() - startTiming) + "ms)");
        Color.log(" &f[*] &6Version&f: &b" + Settings.getVersion);
        Color.log(" &f[*] &6Name&f: &b" + Settings.getName);
        Color.log(" &f[*] &6Author&f: &b" + Settings.getDeveloper);
        Color.log("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        updateCheck(getProxy().getConsole(), true);
    }

    private void loadCommands() {
        BungeeCommandManager manager = new BungeeCommandManager(this);
        manager.getCommandConditions().addCondition("noconsole", (context) -> {
            BungeeCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("Only players can use this command.");
            }
            return;
        });
        manager.registerCommand(new StaffChatCommand(this));
        manager.registerCommand(new ReloadCommand(this));
        manager.registerCommand(new ToggleCommand());
        Color.log("&aLoaded commands.");
    }

    private void loadListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinListener());
        getProxy().getPluginManager().registerListener(this, new ChatListener());
        Color.log("&aLoaded listeners.");
    }

    public void updateCheck(CommandSender sender, boolean console) {
        Color.log("&aChecking for updates!");
        try {
            String urlString = "https://updatecheck.refracdev.ml";
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
                JsonObject info = plugins.get(Settings.getName).getAsJsonObject();
                String version = info.get("version").getAsString();
                if (version.equals(getDescription().getVersion())) {
                    if (console) {
                        sender.sendMessage(Color.translate("&a" + Settings.getName + " is on the latest version."));
                    }
                } else {
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour " + Settings.getName + " version is out of date!"));
                    sender.sendMessage(Color.translate("&cWe recommend updating ASAP!"));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate("&cYour Version: &e" + Settings.getVersion));
                    sender.sendMessage(Color.translate("&aNewest Version: &e" + version));
                    sender.sendMessage(Color.translate(""));
                    sender.sendMessage(Color.translate(""));
                    return;
                }
                return;
            } else {
                sender.sendMessage(Color.translate("&cWrong response from update API, contact plugin developer!"));
                return;
            }
        } catch (
                Exception ex) {
            sender.sendMessage(Color.translate("&cFailed to get updater check. (" + ex.getMessage() + ")"));
            return;
        }
    }
}