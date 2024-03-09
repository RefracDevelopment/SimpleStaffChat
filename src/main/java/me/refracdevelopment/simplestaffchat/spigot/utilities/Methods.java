package me.refracdevelopment.simplestaffchat.spigot.utilities;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public void sendStaffChat(CommandSender sender, String format) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE) && !staffChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(sender, format));
            }
        }

        if (SimpleStaffChat.getInstance().getSettings().BUNGEECORD) {
            SimpleStaffChat.getInstance().getPluginMessage().sendStaffChat(Color.translate(sender, format));
        }
        Color.log2(Color.translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            DiscordImpl.sendStaffChat(player, format
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "»")
            );
        } else {
            DiscordImpl.sendStaffChat(null, format
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»")
            );
        }
    }

    public void sendDevChat(CommandSender sender, String format) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE) && !devChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(sender, format));
            }
        }

        if (SimpleStaffChat.getInstance().getSettings().BUNGEECORD) {
            SimpleStaffChat.getInstance().getPluginMessage().sendDevChat(Color.translate(sender, format));
        }
        Color.log2(Color.translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            DiscordImpl.sendDevChat(player, format
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "»")
            );
        } else {
            DiscordImpl.sendDevChat(null, format
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»")
            );
        }
    }

    public void sendAdminChat(CommandSender sender, String format) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE) && !adminChatMuted.contains(p.getUniqueId())) {
                Color.sendCustomMessage(p, Color.translate(sender, format));
            }
        }

        if (SimpleStaffChat.getInstance().getSettings().BUNGEECORD) {
            SimpleStaffChat.getInstance().getPluginMessage().sendDevChat(Color.translate(sender, format));
        }
        Color.log2(Color.translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            DiscordImpl.sendAdminChat(player, format
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "»")
            );
        } else {
            DiscordImpl.sendAdminChat(null, format
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "»")
            );
        }
    }

    public void toggleStaffChat(Player player) {
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

    public void toggleAdminChat(Player player) {
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

    public void toggleDevChat(Player player) {
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

    public void toggleAllChat(Player player) {
        

        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            devChatPlayers.remove(player.getUniqueId());
            adminChatPlayers.remove(player.getUniqueId());
            Color.sendMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(Player player) {
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

    public void hideAdminChat(Player player) {
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

    public void hideDevChat(Player player) {
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

    public void hideAll(Player player) {
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