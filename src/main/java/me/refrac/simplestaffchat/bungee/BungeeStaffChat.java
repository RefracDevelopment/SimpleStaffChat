/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee;

import lombok.Getter;
import me.refrac.simplestaffchat.bungee.commands.ReloadCommand;
import me.refrac.simplestaffchat.bungee.commands.StaffChatCommand;
import me.refrac.simplestaffchat.bungee.commands.ToggleCommand;
import me.refrac.simplestaffchat.bungee.listeners.ChatListener;
import me.refrac.simplestaffchat.bungee.listeners.JoinListener;
import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.files.Files;
import me.refrac.simplestaffchat.bungee.utilities.Logger;
import me.refrac.simplestaffchat.bungee.utilities.Metrics;
import me.refrac.simplestaffchat.shared.Settings;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class BungeeStaffChat extends Plugin {
    @Getter
    private static BungeeStaffChat instance;

    @Override
    public void onEnable() {
        instance = this;

        Files.loadFiles();
        Config.loadConfig();

        loadCommands();
        loadListeners();

        int pluginId = 12096;
        Metrics metrics = new Metrics(this, pluginId);

        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Logger.NONE.out("&e" + Settings.getName + " has been enabled.");
        Logger.NONE.out(" &f[*] &6Version&f: &b" + Settings.getVersion);
        Logger.NONE.out(" &f[*] &6Name&f: &b" + Settings.getName);
        Logger.NONE.out(" &f[*] &6Author&f: &b" + Settings.getDeveloper);
        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
    }

    private void loadCommands() {
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand());
        getProxy().getPluginManager().registerCommand(this, new ToggleCommand());
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand());
    }

    private void loadListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinListener());
        getProxy().getPluginManager().registerListener(this, new ChatListener());
    }
}