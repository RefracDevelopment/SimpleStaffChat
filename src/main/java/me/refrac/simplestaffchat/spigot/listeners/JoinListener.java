/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.Settings;
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
            Bukkit.broadcast(Color.translate(player, Config.JOIN_FORMAT), "simplestaffchat.join");
        }

        if (player.getUniqueId().toString().equalsIgnoreCase(Settings.getDevUUID)) {
            player.sendMessage(" ");
            player.sendMessage(Color.translate("&aWelcome " + Settings.getName + " Developer!"));
            player.sendMessage(Color.translate("&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a."));
            player.sendMessage(Color.translate("&aPlugin name&7: &f" + Settings.getName));
            player.sendMessage(" ");
            player.sendMessage(Color.translate("&aServer version&7: &f" + Bukkit.getServer().getVersion()));
            player.sendMessage(" ");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (Config.JOIN_ENABLED) return;

        Bukkit.broadcast(Color.translate(player, Config.QUIT_FORMAT), "simplestaffchat.quit");
    }
}