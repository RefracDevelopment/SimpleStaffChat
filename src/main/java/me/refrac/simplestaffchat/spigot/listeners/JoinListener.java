/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.shared.Settings;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Config.JOIN_ENABLED) {
            if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

                p.sendMessage(Color.translate(player, Config.JOIN_FORMAT));
            }
        }

        if (!player.getUniqueId().toString().equalsIgnoreCase(Settings.getDevUUID)) return;

        Color.sendMessage(player, " ", true);
        Color.sendMessage(player, "&aWelcome " + Settings.getName + " Developer!", true);
        Color.sendMessage(player, "&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a.", true);
        Color.sendMessage(player, "&aPlugin name&7: &f" + Settings.getName, true);
        Color.sendMessage(player, " ", true);
        Color.sendMessage(player, "&aServer version&7: &f" + Bukkit.getVersion(), true);
        Color.sendMessage(player, " ", true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (Config.JOIN_ENABLED) return;
        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) return;

            p.sendMessage(Color.translate(player, Config.QUIT_FORMAT));
        }
    }
}