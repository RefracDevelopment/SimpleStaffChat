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
package me.refracdevelopment.simplestaffchat.bungee;

import me.refracdevelopment.simplestaffchat.bungee.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.admin.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.admin.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.dev.DevChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.dev.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.bungee.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.bungee.utilities.files.Files;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Logger;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class BungeeStaffChat extends Plugin {

    @Override
    public void onEnable() {
        Files.loadFiles(this);

        loadCommands();
        loadListeners();

        new Metrics(this, 12096);

        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
        Logger.NONE.out("&e" + Settings.getName + " has been enabled.");
        Logger.NONE.out(" &f[*] &6Version&f: &b" + Settings.getVersion);
        Logger.NONE.out(" &f[*] &6Name&f: &b" + Settings.getName);
        Logger.NONE.out(" &f[*] &6Author&f: &b" + Settings.getDeveloper);
        Logger.NONE.out("&8&m==&c&m=====&f&m======================&c&m=====&8&m==");
    }

    private void loadCommands() {
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ToggleCommand());
        getProxy().getPluginManager().registerCommand(this, new AdminChatCommand(this));
        getProxy().getPluginManager().registerCommand(this, new AdminToggleCommand());
        getProxy().getPluginManager().registerCommand(this, new DevChatCommand(this));
        getProxy().getPluginManager().registerCommand(this, new DevToggleCommand());
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
    }

    private void loadListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinListener(this));
        getProxy().getPluginManager().registerListener(this, new ChatListener(this));
    }
}