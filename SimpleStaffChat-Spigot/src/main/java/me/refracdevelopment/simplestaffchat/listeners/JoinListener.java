package me.refracdevelopment.simplestaffchat.listeners;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.DiscordImpl;
import me.refracdevelopment.simplestaffchat.utilities.JoinType;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;


public class JoinListener implements Listener {

    private final SimpleStaffChat plugin;

    public JoinListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN))
            return;

        RyMessageUtils.sendStaffChat(player, Permissions.STAFFCHAT_SEE, this.plugin.getSettings().JOIN_FORMAT
                .replace("%player%", player.getName())
        );

        RyMessageUtils.sendConsole(true, this.plugin.getSettings().JOIN_FORMAT
                .replace("%player%", player.getName())
        );

        DiscordImpl.sendJoin(JoinType.JOIN, player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT))
            return;

        RyMessageUtils.sendStaffChat(player, Permissions.STAFFCHAT_SEE, this.plugin.getSettings().QUIT_FORMAT
                .replace("%player%", player.getName())
        );

        RyMessageUtils.sendConsole(true, this.plugin.getSettings().QUIT_FORMAT
                .replace("%player%", player.getName())
        );

        DiscordImpl.sendJoin(JoinType.LEAVE, player);
    }
}