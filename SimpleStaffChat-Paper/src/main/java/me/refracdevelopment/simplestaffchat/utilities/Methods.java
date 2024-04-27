package me.refracdevelopment.simplestaffchat.utilities;

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public final class Methods {
    @Getter
    private static final List<UUID> staffChatMuted = new ArrayList<>();
    @Getter
    private static final List<UUID> adminChatMuted = new ArrayList<>();
    @Getter
    private static final List<UUID> devChatMuted = new ArrayList<>();
    @Getter
    private static final List<UUID> staffChatPlayers = new ArrayList<>();
    @Getter
    private static final List<UUID> adminChatPlayers = new ArrayList<>();
    @Getter
    private static final List<UUID> devChatPlayers = new ArrayList<>();

    private Methods() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void sendStaffChat(CommandSender sender, String format, String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("simplestaffchat.staffchat.see") && !staffChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(sender, format).toString());
            }
        }

        Color.log2(Color.translate(sender, format).toString());

        if (sender instanceof Player) {
            DiscordImpl.sendStaffChat(sender, ChatColor.stripColor(message)
                    .replace("%server%", (SimpleStaffChat.getInstance().getSettings()).SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»"));
        } else {
            DiscordImpl.sendStaffChat(null, ChatColor.stripColor(message)
                    .replace("%server%", (SimpleStaffChat.getInstance().getSettings()).SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»"));
        }

    }

    public static void sendDevChat(CommandSender sender, String format, String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("simplestaffchat.devchat.see") && !devChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(sender, format).toString());
            }
        }

        Color.log2(Color.translate(sender, format).toString());

        if (sender instanceof Player) {
            DiscordImpl.sendDevChat(sender, ChatColor.stripColor(message)
                    .replace("%server%", (SimpleStaffChat.getInstance().getSettings()).SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»"));
        } else {
            DiscordImpl.sendDevChat(null, ChatColor.stripColor(message)
                    .replace("%server%", (SimpleStaffChat.getInstance().getSettings()).SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»"));
        }

    }


    public static void sendAdminChat(CommandSender sender, String format, String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("simplestaffchat.adminchat.see") && !adminChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(sender, format).toString());
            }
        }

        Color.log2(Color.translate(sender, format).toString());

        if (sender instanceof Player) {
            DiscordImpl.sendAdminChat(sender, ChatColor.stripColor(message)
                    .replace("%server%", (SimpleStaffChat.getInstance().getSettings()).SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%displayname%", ChatColor.stripColor(sender.getName()))
                    .replace("%arrow%", "»"));
        } else {
            DiscordImpl.sendAdminChat(null, ChatColor.stripColor(message)
                    .replace("%server%", (SimpleStaffChat.getInstance().getSettings()).SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»"));
        }

    }


    public static void toggleStaffChat(Player player) {
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

    public static void toggleAdminChat(Player player) {
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

    public static void toggleDevChat(Player player) {
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

    public static void toggleAllChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId()) || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            devChatPlayers.remove(player.getUniqueId());
            adminChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, "allchat-toggle-on");
        }
    }

    public static void hideStaffChat(Player player) {
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

    public static void hideAdminChat(Player player) {
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

    public static void hideDevChat(Player player) {
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

    public static void hideAll(Player player) {
        if ((staffChatMuted.contains(player.getUniqueId()) && adminChatMuted.contains(player.getUniqueId())) || devChatMuted.contains(player.getUniqueId())) {
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