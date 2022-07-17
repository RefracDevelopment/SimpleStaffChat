/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 RefracDevelopment
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package me.refracdevelopment.simplestaffchat.spigot;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.ConditionFailedException;
import me.refracdevelopment.simplestaffchat.spigot.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.admin.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.admin.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.CommandPreprocessListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.spigot.utilities.files.Files;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import me.refracdevelopment.simplestaffchat.spigot.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleStaffChat extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Files.loadFiles(this);

        loadCommands();
        loadListeners();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Logger.INFO.out("Hooked into PlaceholderAPI.");
        }

        new Metrics(this, 12095);

        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Logger.NONE.out("&e" + Settings.getName + " has been enabled.");
        Logger.NONE.out(" &f[*] &6Version&f: &b" + Settings.getVersion);
        Logger.NONE.out(" &f[*] &6Name&f: &b" + Settings.getName);
        Logger.NONE.out(" &f[*] &6Author&f: &b" + Settings.getDeveloper);
        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
    }

    private void loadCommands() {
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.getCommandConditions().addCondition("noconsole", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("Only players can execute this command.");
            }
        });
        manager.registerCommand(new StaffChatCommand());
        manager.registerCommand(new AdminChatCommand());
        manager.registerCommand(new DevChatCommand());
        manager.registerCommand(new ToggleCommand());
        manager.registerCommand(new AdminToggleCommand());
        manager.registerCommand(new DevToggleCommand());
        manager.registerCommand(new ReloadCommand());
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandPreprocessListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }
}