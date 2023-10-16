package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

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

    public Methods(VelocityStaffChat plugin) {
        super(plugin);
    }

    public void sendStaffChat(CommandSource commandSource, String format, String message) {
        for (Player p : plugin.getServer().getAllPlayers()) {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(commandSource, format));
            }
        }
        plugin.getColor().log2(plugin.getColor().translate(commandSource, format));
        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;
            plugin.getDiscordImpl().sendStaffChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            plugin.getDiscordImpl().sendStaffChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSource commandSource, String format, String message) {
        for (Player p : plugin.getServer().getAllPlayers()) {
            if (p.hasPermission(plugin.getPermissions().DEVCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(commandSource, format));
            }
        }
        plugin.getColor().log2(plugin.getColor().translate(commandSource, format));
        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;
            plugin.getDiscordImpl().sendDevChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            plugin.getDiscordImpl().sendDevChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSource commandSource, String format, String message) {
        for (Player p : plugin.getServer().getAllPlayers()) {
            if (p.hasPermission(plugin.getPermissions().ADMINCHAT_SEE)) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(commandSource, format));
            }
        }
        plugin.getColor().log2(plugin.getColor().translate(commandSource, format));
        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;
            plugin.getDiscordImpl().sendAdminChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            plugin.getDiscordImpl().sendAdminChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void toggleStaffChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().STAFFCHAT_TOGGLE_OFF);
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                devChatPlayers.remove(player.getUniqueId());
            }
            staffChatPlayers.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().STAFFCHAT_TOGGLE_ON);
        }
    }

    public void toggleAdminChat(Player player) {
        if (adminChatPlayers.contains(player.getUniqueId())) {
            adminChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().ADMINCHAT_TOGGLE_OFF);
        } else {
            if (devChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                devChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            adminChatPlayers.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().ADMINCHAT_TOGGLE_ON);
        }
    }

    public void toggleDevChat(Player player) {
        if (devChatPlayers.contains(player.getUniqueId())) {
            devChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().DEVCHAT_TOGGLE_OFF);
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }
            devChatPlayers.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().DEVCHAT_TOGGLE_ON);
        }
    }

    public void toggleAllChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().ALLCHAT_TOGGLE_ON);
        }
    }

    public void hideStaffChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());

        } else {
            staffChatMuted.add(player.getUniqueId());
        }
    }

    public void hideAdminChat(Player player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
        } else {
            adminChatMuted.add(player.getUniqueId());
        }
    }

    public void hideDevChat(Player player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
        } else {
            devChatMuted.add(player.getUniqueId());
        }
    }
}