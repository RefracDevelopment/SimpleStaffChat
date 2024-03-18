package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class Methods {

    @Getter private List<UUID> staffChatMuted = new ArrayList<>();
    @Getter private List<UUID> adminChatMuted = new ArrayList<>();
    @Getter private List<UUID> devChatMuted = new ArrayList<>();
    @Getter private List<UUID> staffChatPlayers = new ArrayList<>();
    @Getter private List<UUID> adminChatPlayers = new ArrayList<>();
    @Getter private List<UUID> devChatPlayers = new ArrayList<>();

    public void sendStaffChat(CommandSource commandSource, String format, String message) {
        for (Player p : VelocityStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(commandSource, format));
            }
        }

        Color.log2(format);

        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;

            if (!player.getCurrentServer().isPresent())
                return;

            DiscordImpl.sendStaffChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            DiscordImpl.sendStaffChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSource commandSource, String format, String message) {
        for (Player p : VelocityStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                p.sendMessage(Color.translate(commandSource, format));
            }
        }

        Color.log2(format);

        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;

            if (!player.getCurrentServer().isPresent())
                return;

            DiscordImpl.sendDevChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            DiscordImpl.sendDevChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSource commandSource, String format, String message) {
        for (Player p : VelocityStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                p.sendMessage(Color.translate(commandSource, format));
            }
        }

        Color.log2(format);

        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;

            if (!player.getCurrentServer().isPresent())
                return;

            DiscordImpl.sendAdminChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            DiscordImpl.sendAdminChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void toggleStaffChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().STAFFCHAT_TOGGLE_OFF);
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                devChatPlayers.remove(player.getUniqueId());
            }
            staffChatPlayers.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().STAFFCHAT_TOGGLE_ON);
        }
    }

    public void toggleAdminChat(Player player) {
        if (adminChatPlayers.contains(player.getUniqueId())) {
            adminChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ADMINCHAT_TOGGLE_OFF);
        } else {
            if (devChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                devChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            adminChatPlayers.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ADMINCHAT_TOGGLE_ON);
        }
    }

    public void toggleDevChat(Player player) {
        if (devChatPlayers.contains(player.getUniqueId())) {
            devChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().DEVCHAT_TOGGLE_OFF);
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            devChatPlayers.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().DEVCHAT_TOGGLE_ON);
        }
    }

    public void toggleAllChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ALLCHAT_TOGGLE_ON);
        }
    }

    public void hideStaffChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().STAFFCHAT_MUTED_OFF);
        } else {
            if (devChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                devChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            staffChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().STAFFCHAT_MUTED_ON);
        }
    }

    public void hideAdminChat(Player player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ADMINCHAT_MUTED_OFF);
        } else {
            if (devChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                devChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            adminChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ADMINCHAT_MUTED_ON);
        }
    }

    public void hideDevChat(Player player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().DEVCHAT_MUTED_OFF);
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            devChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().DEVCHAT_MUTED_ON);
        }
    }

    public void hideAllChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId()) || adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            adminChatMuted.remove(player.getUniqueId());
            devChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ALLCHAT_MUTED_OFF);
        } else {
            staffChatMuted.add(player.getUniqueId());
            adminChatMuted.add(player.getUniqueId());
            devChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ALLCHAT_MUTED_ON);
        }
    }
}