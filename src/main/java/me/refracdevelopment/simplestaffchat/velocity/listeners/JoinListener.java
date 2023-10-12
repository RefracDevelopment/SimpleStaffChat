package me.refracdevelopment.simplestaffchat.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

public class JoinListener {

    @Subscribe
    public void onJoin(ServerPostConnectEvent event) {
        Player player = event.getPlayer();

        if (player.getUniqueId().equals(Settings.getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(Settings.getDevUUID2)) {
            sendDevMessage(player);
        }

        if (!Config.JOIN_ENABLED.getBoolean()) return;
        if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;
        if (event.getPreviousServer() != null) return;
        if (!player.getCurrentServer().isPresent()) return;

        VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.JOIN_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.JOIN_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
        VelocityStaffChat.getInstance().getDiscord().sendJoin(JoinType.JOIN, player, player.getCurrentServer().get().getServerInfo().getName(), "");
    }

    @Subscribe
    public void onSwitch(ServerPostConnectEvent event) {
        if (!Config.JOIN_ENABLED.getBoolean()) return;
        if (event.getPreviousServer() == null) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH)) return;
        if (!player.getCurrentServer().isPresent()) return;

        VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.SWITCH_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServer().getServerInfo().getName())
                        .replace("%from%", event.getPreviousServer().getServerInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.SWITCH_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                .replace("%from%", event.getPreviousServer().getServerInfo().getName())));
        VelocityStaffChat.getInstance().getDiscord().sendJoin(JoinType.SWITCH, player, player.getCurrentServer().get().getServerInfo().getName(), event.getPreviousServer().getServerInfo().getName());
    }

    @Subscribe
    public void onQuit(DisconnectEvent event) {
        if (!Config.JOIN_ENABLED.getBoolean()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;

        VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.QUIT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.QUIT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
        VelocityStaffChat.getInstance().getDiscord().sendJoin(JoinType.LEAVE, player, player.getCurrentServer().get().getServerInfo().getName(), "");
    }

    private void sendDevMessage(Player player) {
        Color.sendMessage(player, " ");
        Color.sendMessage(player, "&aWelcome " + VelocityStaffChat.getInstance().getContainer().getDescription().getName().get() + " Developer!");
        Color.sendMessage(player, "&aThis server is currently running " + VelocityStaffChat.getInstance().getContainer().getDescription().getName().get() + " &bv" + VelocityStaffChat.getInstance().getContainer().getDescription().getVersion().get() + "&a.");
        Color.sendMessage(player, "&aPlugin name&7: &f" + VelocityStaffChat.getInstance().getContainer().getDescription().getName().get());
        Color.sendMessage(player, " ");
        Color.sendMessage(player, "&aServer version&7: &f" + VelocityStaffChat.getInstance().getServer().getVersion());
        Color.sendMessage(player, " ");
    }
}