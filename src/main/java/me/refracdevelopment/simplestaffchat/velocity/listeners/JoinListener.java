package me.refracdevelopment.simplestaffchat.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

public class JoinListener {

    @Subscribe
    public void onJoin(ServerConnectedEvent event) {
        Player player = event.getPlayer();

        if (Config.JOIN_ENABLED.getBoolean()) {
            if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;
            if (player.getCurrentServer().isPresent()) return;

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, Config.JOIN_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
                }
            });
            Color.log2(Color.translate(player, Config.JOIN_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
        }

        if (player.getUniqueId().equals(Settings.getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().equals(Settings.getDevUUID2)) {
            sendDevMessage(player);
        }
    }

    @Subscribe
    public void onSwitch(ServerConnectedEvent event) {
        if (!event.getPreviousServer().isPresent()) return;
        if (!Config.JOIN_ENABLED.getBoolean()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_SWITCH)) return;

        VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.SWITCH_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                        .replace("%from%", event.getPreviousServer().get().getServerInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.SWITCH_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                .replace("%from%", event.getPreviousServer().get().getServerInfo().getName())));
    }

    @Subscribe
    public void onQuit(KickedFromServerEvent event) {
        if (!Config.JOIN_ENABLED.getBoolean()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;
        if (!player.getCurrentServer().isPresent()) return;

        VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(player, Config.QUIT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
            }
        });
        Color.log2(Color.translate(player, Config.QUIT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())));
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