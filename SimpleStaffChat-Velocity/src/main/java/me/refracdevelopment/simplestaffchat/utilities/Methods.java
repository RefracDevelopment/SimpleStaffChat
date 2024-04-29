package me.refracdevelopment.simplestaffchat.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class Methods {

    @Getter
    private final List<UUID> staffChatMuted = new ArrayList<>();
    @Getter
    private final List<UUID> adminChatMuted = new ArrayList<>();
    @Getter
    private final List<UUID> devChatMuted = new ArrayList<>();
    @Getter
    private final List<UUID> staffChatPlayers = new ArrayList<>();
    @Getter
    private final List<UUID> adminChatPlayers = new ArrayList<>();
    @Getter
    private final List<UUID> devChatPlayers = new ArrayList<>();

    public void sendStaffChat(CommandSource commandSource, String format, String message) {
        for (Player p : SimpleStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE) && SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(p.getCurrentServer().get().getServer().getServerInfo().getName())) {
                p.sendMessage(Color.translate(commandSource, format));
            }
        }

        Color.log2(format);

        if (commandSource instanceof Player player) {
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
        for (Player p : SimpleStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE) && SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(p.getCurrentServer().get().getServerInfo().getName())) {
                p.sendMessage(Color.translate(commandSource, format));
            }
        }

        Color.log2(format);

        if (commandSource instanceof Player player) {
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
        for (Player p : SimpleStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE) && SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(p.getCurrentServer().get().getServerInfo().getName())) {
                p.sendMessage(Color.translate(commandSource, format));
            }
        }

        Color.log2(format);

        if (commandSource instanceof Player player) {
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
            Color.sendConfigMessage(player, "staffchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                devChatPlayers.remove(player.getUniqueId());
            }
            staffChatPlayers.add(player.getUniqueId());
            Color.sendConfigMessage(player, "staffchat-toggle-on");
        }
    }

    public void toggleAdminChat(Player player) {
        if (adminChatPlayers.contains(player.getUniqueId())) {
            adminChatPlayers.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "adminchat-toggle-off");
        } else {
            if (devChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                devChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }

            adminChatPlayers.add(player.getUniqueId());
            Color.sendConfigMessage(player, "adminchat-toggle-on");
        }
    }

    public void toggleDevChat(Player player) {
        if (devChatPlayers.contains(player.getUniqueId())) {
            devChatPlayers.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "devchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }

            devChatPlayers.add(player.getUniqueId());
            Color.sendConfigMessage(player, "devchat-toggle-on");
        }
    }

    public void toggleAllChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "staffchat-muted-off");
        } else {
            if (devChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                devChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }

            staffChatMuted.add(player.getUniqueId());
            Color.sendConfigMessage(player, "staffchat-muted-on");
        }
    }

    public void hideAdminChat(Player player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "adminchat-muted-off");
        } else {
            if (devChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                devChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            adminChatMuted.add(player.getUniqueId());
            Color.sendConfigMessage(player, "adminchat-muted-on");
        }
    }

    public void hideDevChat(Player player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "devchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }

            devChatMuted.add(player.getUniqueId());
            Color.sendConfigMessage(player, "devchat-muted-on");
        }
    }

    public void hideAllChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId()) || adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            adminChatMuted.remove(player.getUniqueId());
            devChatMuted.remove(player.getUniqueId());
            Color.sendConfigMessage(player, "allchat-muted-off");
        } else {
            staffChatMuted.add(player.getUniqueId());
            adminChatMuted.add(player.getUniqueId());
            devChatMuted.add(player.getUniqueId());
            Color.sendConfigMessage(player, "allchat-muted-on");
        }
    }
}