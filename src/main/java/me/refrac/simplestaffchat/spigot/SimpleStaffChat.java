/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot;

import lombok.Getter;
import me.refrac.simplestaffchat.spigot.commands.ReloadCommand;
import me.refrac.simplestaffchat.spigot.commands.ToggleCommand;
import me.refrac.simplestaffchat.spigot.listeners.ChatListener;
import me.refrac.simplestaffchat.spigot.listeners.JoinListener;
import me.refrac.simplestaffchat.spigot.utilities.Files;
import me.refrac.simplestaffchat.spigot.utilities.Metrics;
import me.refrac.simplestaffchat.spigot.utilities.Settings;
import me.refrac.simplestaffchat.spigot.commands.StaffChatCommand;
import me.refrac.simplestaffchat.spigot.utilities.Logger;
import me.refrac.simplestaffchat.spigot.utilities.updatechecker.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SimpleStaffChat extends JavaPlugin {
    @Getter
    private static SimpleStaffChat instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        Files.loadFiles(this);

        loadCommands();
        loadListeners();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Logger.INFO.out("Hooked into PlaceholderAPI.");
        }

        int pluginId = 12095;
        Metrics metrics = new Metrics(this, pluginId);

        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Logger.NONE.out("&e" + Settings.getName + " has been enabled.");
        Logger.NONE.out(" &f[*] &6Version&f: &b" + Settings.getVersion);
        Logger.NONE.out(" &f[*] &6Name&f: &b" + Settings.getName);
        Logger.NONE.out(" &f[*] &6Author&f: &b" + Settings.getDeveloper);
        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");

        Logger.INFO.out("Checking for updates...");
        new UpdateChecker(this, 91883).getLatestVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Logger.NONE.out("&7&m-----------------------------------------");
                Logger.NONE.out("&bA new version of " + Settings.getName + " &bhas been released!");
                Logger.NONE.out("&bPlease update here: &e" + Settings.getPluginURL);
                Logger.NONE.out("&7&m-----------------------------------------");
            } else
                Logger.SUCCESS.out(Settings.getName + " is up to date!");
        });
    }

    private void loadCommands() {
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        getCommand("staffchattoggle").setExecutor(new ToggleCommand());
        getCommand("staffchatreload").setExecutor(new ReloadCommand());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }
}