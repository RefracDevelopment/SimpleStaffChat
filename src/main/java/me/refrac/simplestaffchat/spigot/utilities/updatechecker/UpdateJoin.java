package me.refrac.simplestaffchat.spigot.utilities.updatechecker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateJoin implements Listener {

    @EventHandler
    public void onUpdateJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }
}