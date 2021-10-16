package me.refrac.simplestaffchat.spigot.listeners;

import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onDevJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equalsIgnoreCase("Refracxx")) {
            player.sendMessage(" ");
            player.sendMessage(Color.translate("&aWelcome " + SimpleStaffChat.getInstance().getDescription().getName() + " Developer!"));
            player.sendMessage(Color.translate("&aThis server is currently running " + SimpleStaffChat.getInstance().getDescription().getName() + " &bv" + SimpleStaffChat.getInstance().getDescription().getVersion() + "&a."));
            player.sendMessage(Color.translate("&aPlugin name&7: &f" + SimpleStaffChat.getInstance().getDescription().getName()));
            player.sendMessage(" ");
            player.sendMessage(Color.translate("&aServer version&7: &f" + Bukkit.getServer().getVersion()));
            player.sendMessage(" ");
        }
    }
}