/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.spigot.utilities.updatechecker.UpdateChecker;
import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import me.refrac.simplestaffchat.spigot.utilities.Settings;
import me.refrac.simplestaffchat.spigot.utilities.Files;
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

        if (Files.getConfig().getBoolean("update.enabled")) {
            if (!player.hasPermission("simplestaffchat.update")) return;

            new UpdateChecker(SimpleStaffChat.getInstance(), 91883).getLatestVersion(version -> {
                if (!SimpleStaffChat.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                    player.sendMessage(Color.translate("&7&m-----------------------------------------"));
                    player.sendMessage(Color.translate("&bA new version of " + Settings.getName + " &bhas been released!"));
                    player.sendMessage(Color.translate("&bPlease update here: &e" + Settings.getPluginURL));
                    player.sendMessage(Color.translate("&7&m-----------------------------------------"));
                }
            });
        }

        if (Files.getConfig().getBoolean("join.enabled")) {
            if (!player.hasPermission("simplestaffchat.join")) return;

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("simplestaffchat.join")) return;

                p.sendMessage(Color.translate(player, Files.getConfig().getString("join.join-format")));
            }
        }

        if (player.getName().equalsIgnoreCase("Refracxx")) {
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

        if (!Files.getConfig().getBoolean("join.enabled")) return;
        if (!player.hasPermission("simplestaffchat.quit")) return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("simplestaffchat.quit")) return;

            p.sendMessage(Color.translate(player, Files.getConfig().getString("join.quit-format")));
        }
    }
}