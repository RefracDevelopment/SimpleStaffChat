package me.refracdevelopment.simplestaffchat.bungee.utilities;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class Methods {

    @Getter private final List<UUID> staffChatMuted = new ArrayList<>();
    @Getter private final List<UUID> adminChatMuted = new ArrayList<>();
    @Getter private final List<UUID> devChatMuted = new ArrayList<>();
    @Getter private final List<UUID> staffChatPlayers = new ArrayList<>();
    @Getter private final List<UUID> adminChatPlayers = new ArrayList<>();
    @Getter private final List<UUID> devChatPlayers = new ArrayList<>();

    public void sendStaffChat(CommandSender commandSender, String format, String message) {
        for (ProxiedPlayer p : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE) && !staffChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(commandSender, format));
            }
        }

        Color.log2(Color.translate(commandSender, format));

        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            DiscordImpl.sendStaffChat(player, ChatColor.stripColor(message)
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            DiscordImpl.sendStaffChat(null, ChatColor.stripColor(message)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSender commandSender, String format, String message) {
        for (ProxiedPlayer p : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE) && !devChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(commandSender, format));
            }
        }

        Color.log2(Color.translate(commandSender, format));

        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            DiscordImpl.sendDevChat(player, ChatColor.stripColor(message)
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            DiscordImpl.sendDevChat(null, ChatColor.stripColor(message)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSender commandSender, String format, String message) {
        for (ProxiedPlayer p : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE) && !adminChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(commandSender, format));
            }
        }

        Color.log2(Color.translate(commandSender, format));

        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            DiscordImpl.sendAdminChat(player, ChatColor.stripColor(message)
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            DiscordImpl.sendAdminChat(null, ChatColor.stripColor(message)
                    .replace("%player%", "Console")
            );
        }
    }

    public void toggleStaffChat(ProxiedPlayer player) {
        if (staffChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, "staffchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                devChatPlayers.remove(player.getUniqueId());
            }
            staffChatPlayers.add(player.getUniqueId());
            Color.sendMessage(player, "staffchat-toggle-on");
        }
    }

    public void toggleAdminChat(ProxiedPlayer player) {
        if (adminChatPlayers.contains(player.getUniqueId())) {
            adminChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, "adminchat-toggle-off");
        } else {
            if (devChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                devChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            adminChatPlayers.add(player.getUniqueId());
            Color.sendMessage(player, "adminchat-toggle-on");
        }
    }

    public void toggleDevChat(ProxiedPlayer player) {
        if (devChatPlayers.contains(player.getUniqueId())) {
            devChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, "devchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            devChatPlayers.add(player.getUniqueId());
            Color.sendMessage(player, "devchat-toggle-on");
        }
    }

    public void toggleAllChat(ProxiedPlayer player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            devChatPlayers.remove(player.getUniqueId());
            adminChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(ProxiedPlayer player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, "staffchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
            }
            staffChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, "staffchat-muted-on");
        }
    }

    public void hideAdminChat(ProxiedPlayer player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, "adminchat-muted-off");
        } else {
            if (staffChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                staffChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
            }
            adminChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, "adminchat-muted-on");
        }
    }

    public void hideDevChat(ProxiedPlayer player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, "devchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            devChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, "devchat-muted-on");
        }
    }

    public void hideAll(ProxiedPlayer player) {
        if (staffChatMuted.contains(player.getUniqueId()) && adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            adminChatMuted.remove(player.getUniqueId());
            devChatMuted.remove(player.getUniqueId());
            Color.sendMessage(player, "allchat-muted-off");
        } else {
            staffChatMuted.add(player.getUniqueId());
            adminChatMuted.add(player.getUniqueId());
            devChatMuted.add(player.getUniqueId());
            Color.sendMessage(player, "allchat-muted-on");
        }
    }
}