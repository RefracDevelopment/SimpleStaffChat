package me.refracdevelopment.simplestaffchat.spigot.utilities;

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Methods extends Manager {

    private List<UUID> staffChatMuted = new ArrayList<>();
    private List<UUID> adminChatMuted = new ArrayList<>();
    private List<UUID> devChatMuted = new ArrayList<>();
    private List<UUID> staffChatPlayers = new ArrayList<>();
    private List<UUID> adminChatPlayers = new ArrayList<>();
    private List<UUID> devChatPlayers = new ArrayList<>();

    public Methods(SimpleStaffChat plugin) {
        super(plugin);
    }

    public void sendStaffChat(CommandSender sender, String format) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE) && !staffChatMuted.contains(p.getUniqueId())) {
                plugin.getColor().sendCustomMessage(p, plugin.getColor().translate(sender, format));
            }
        }

        if (plugin.getSettings().BUNGEECORD && sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getPluginMessage().sendStaffChat(player, plugin.getColor().translate(sender, format));
        }
        plugin.getColor().log2(plugin.getColor().translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getDiscordImpl().sendStaffChat(player, format
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "\u00BB")
            );
        } else {
            plugin.getDiscordImpl().sendStaffChat(null, format
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "\u00BB")
            );
        }
    }

    public void sendDevChat(CommandSender sender, String format) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission(plugin.getPermissions().DEVCHAT_SEE) && !devChatMuted.contains(p.getUniqueId())) {
                plugin.getColor().sendCustomMessage(p, plugin.getColor().translate(sender, format));
            }
        }

        if (plugin.getSettings().BUNGEECORD && sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getPluginMessage().sendDevChat(player, plugin.getColor().translate(sender, format));
        }
        plugin.getColor().log2(plugin.getColor().translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getDiscordImpl().sendDevChat(player, format
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "\u00BB")
            );
        } else {
            plugin.getDiscordImpl().sendDevChat(null, format
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "\u00BB")
            );
        }
    }

    public void sendAdminChat(CommandSender sender, String format) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission(plugin.getPermissions().ADMINCHAT_SEE) && !adminChatMuted.contains(p.getUniqueId())) {
                plugin.getColor().sendCustomMessage(p, plugin.getColor().translate(sender, format));
            }
        }

        if (plugin.getSettings().BUNGEECORD && sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getPluginMessage().sendDevChat(player, plugin.getColor().translate(sender, format));
        }
        plugin.getColor().log2(plugin.getColor().translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getDiscordImpl().sendAdminChat(player, format
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "\u00BB")
            );
        } else {
            plugin.getDiscordImpl().sendAdminChat(null, format
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "\u00BB")
            );
        }
    }

    public void toggleStaffChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                devChatPlayers.remove(player.getUniqueId());
            }
            staffChatPlayers.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, "toggle-on");
        }
    }

    public void toggleAdminChat(Player player) {
        if (adminChatPlayers.contains(player.getUniqueId())) {
            adminChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "adminchat-toggle-off");
        } else {
            if (devChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                devChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            adminChatPlayers.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, "adminchat-toggle-on");
        }
    }

    public void toggleDevChat(Player player) {
        if (devChatPlayers.contains(player.getUniqueId())) {
            devChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "devchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            devChatPlayers.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, "devchat-toggle-on");
        }
    }

    public void toggleAllChat(Player player) {
        

        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            devChatPlayers.remove(player.getUniqueId());
            adminChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "staffchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
            }
            staffChatMuted.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, "staffchat-muted-on");
        }
    }

    public void hideAdminChat(Player player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "adminchat-muted-off");
        } else {
            if (staffChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                staffChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
            }
            adminChatMuted.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, "adminchat-muted-on");
        }
    }

    public void hideDevChat(Player player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, "devchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            devChatMuted.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, "devchat-muted-on");
        }
    }
}