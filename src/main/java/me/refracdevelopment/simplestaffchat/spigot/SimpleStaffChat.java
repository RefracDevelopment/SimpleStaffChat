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

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.config.ConfigFile;
import me.refracdevelopment.simplestaffchat.spigot.listeners.ChatListener;
import me.refracdevelopment.simplestaffchat.spigot.listeners.JoinListener;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import me.refracdevelopment.simplestaffchat.spigot.utilities.CommandManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SimpleStaffChat extends JavaPlugin {

    @Getter
    private static SimpleStaffChat instance;
    private ConfigFile configFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.configFile = new ConfigFile(this, "config.yml");
        Config.setConfig(this.configFile);
        Config.load();
        Logger.NONE.out("&c==========================================");
        Logger.NONE.out("&aAll files have been loaded correctly!");
        Logger.NONE.out("&c==========================================");

        CommandManager.registerAll();
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

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }
}