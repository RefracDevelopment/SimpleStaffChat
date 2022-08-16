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
package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Manager;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.bungee.config.Config;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener extends Manager implements Listener {

    public JoinListener(BungeeStaffChat plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (Config.JOIN_ENABLED.toBoolean()) {
            if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;
            if (player.getServer() != null) return;

            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, Config.JOIN_FORMAT.toString().replace("%server%", event.getTarget().getName())));
                }
            }
            plugin.getProxy().getConsole().sendMessage(Color.translate(player, Config.JOIN_FORMAT.toString().replace("%server%", event.getTarget().getName())));
        }

        if (player.getName().equalsIgnoreCase("Refracxx")) {
            sendDevMessage(player);
        } else if (player.getName().equalsIgnoreCase("RyanMood")) {
            sendDevMessage(player);
        }
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getFrom() == null) return;
        if (!Config.JOIN_ENABLED.toBoolean()) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH)) return;

        for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(player, Config.SWITCH_FORMAT.toString().replace("%server%", player.getServer().getInfo().getName())
                        .replace("%from%", event.getFrom().getName())));
            }
        }
        plugin.getProxy().getConsole().sendMessage(Color.translate(player, Config.SWITCH_FORMAT.toString().replace("%server%", player.getServer().getInfo().getName())
                        .replace("%from%", event.getFrom().getName())));
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (!Config.JOIN_ENABLED.toBoolean()) return;

        ProxiedPlayer player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;
        if (player.getServer() == null) return;

        for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(player, Config.QUIT_FORMAT.toString().replace("%server%", player.getServer().getInfo().getName())));
            }
        }
        plugin.getProxy().getConsole().sendMessage(Color.translate(player, Config.QUIT_FORMAT.toString().replace("%server%", player.getServer().getInfo().getName())));
    }

    private void sendDevMessage(ProxiedPlayer player) {
        Color.sendMessage(player, " ", false, false);
        Color.sendMessage(player, "&aWelcome " + Settings.getName + " Developer!", true, false);
        Color.sendMessage(player, "&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a.", true, false);
        Color.sendMessage(player, "&aPlugin name&7: &f" + Settings.getName, true, false);
        Color.sendMessage(player, " ", false, false);
        Color.sendMessage(player, "&aServer version&7: &f" + plugin.getProxy().getVersion(), true, false);
        Color.sendMessage(player, " ", false, false);
    }
}