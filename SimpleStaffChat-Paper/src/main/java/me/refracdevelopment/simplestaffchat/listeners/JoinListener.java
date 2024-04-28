package me.refracdevelopment.simplestaffchat.listeners;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.utilities.JoinType;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;


public class JoinListener implements Listener {

    private final SimpleStaffChat plugin;
    private final UUID getDevUUID = UUID.fromString("d9c670ed-d7d5-45fb-a144-8b8be86c4a2d");
    private final UUID getDevUUID2 = UUID.fromString("ab898e40-9088-45eb-9d69-e0b78e872627");

    public JoinListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getUniqueId().equals(this.getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(this.getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!player.hasPermission("simplestaffchat.join"))
            return;


        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("simplestaffchat.staffchat.see")) {
                Color.sendCustomMessage(p, Color.translate(player, (this.plugin.getSettings()).JOIN_FORMAT).toString());
            }
        }

        Color.log2(Color.translate(player, (this.plugin.getSettings()).JOIN_FORMAT).toString());
        DiscordImpl.sendJoin(JoinType.JOIN, player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("simplestaffchat.quit"))
            return;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("simplestaffchat.staffchat.see")) {
                Color.sendCustomMessage(p, Color.translate(player, (this.plugin.getSettings()).QUIT_FORMAT).toString());
            }
        }

        Color.log2(Color.translate(player, (this.plugin.getSettings()).QUIT_FORMAT).toString());
        DiscordImpl.sendJoin(JoinType.LEAVE, player);
    }

    private void sendDevMessage(Player player) {
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "&aWelcome " + this.plugin.getDescription().getName() + " Developer!");
        Color.sendCustomMessage(player, "&aThis server is currently running " + this.plugin.getDescription().getName() + " &bv" + this.plugin.getDescription().getVersion() + "&a.");
        Color.sendCustomMessage(player, "&aPlugin name&7: &f" + this.plugin.getDescription().getName());
        Color.sendCustomMessage(player, " ");
        Color.sendCustomMessage(player, "&aServer version&7: &f" + Bukkit.getVersion());
        Color.sendCustomMessage(player, " ");
    }
}