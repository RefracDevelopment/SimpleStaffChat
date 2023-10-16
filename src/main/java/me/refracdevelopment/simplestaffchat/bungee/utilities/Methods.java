package me.refracdevelopment.simplestaffchat.bungee.utilities;

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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

    public Methods(BungeeStaffChat plugin) {
        super(plugin);
    }

    public void sendStaffChat(CommandSender commandSender, String format) {
        for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
            if (p.hasPermission(plugin.getPermissions().STAFFCHAT_SEE) && !staffChatMuted.contains(p.getUniqueId())) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(commandSender, format));
            }
        }
        plugin.getColor().log2(plugin.getColor().translate(commandSender, format));
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            plugin.getDiscordImpl().sendStaffChat(player, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            plugin.getDiscordImpl().sendStaffChat(null, format
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSender commandSender, String format) {
        for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
            if (p.hasPermission(plugin.getPermissions().DEVCHAT_SEE) && !devChatMuted.contains(p.getUniqueId())) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(commandSender, format));
            }
        }
        plugin.getColor().log2(plugin.getColor().translate(commandSender, format));
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            plugin.getDiscordImpl().sendDevChat(player, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            plugin.getDiscordImpl().sendDevChat(null, format
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSender commandSender, String format) {
        for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
            if (p.hasPermission(plugin.getPermissions().ADMINCHAT_SEE) && !adminChatMuted.contains(p.getUniqueId())) {
                plugin.getColor().sendMessage(p, plugin.getColor().translate(commandSender, format));
            }
        }
        plugin.getColor().log2(plugin.getColor().translate(commandSender, format));
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            plugin.getDiscordImpl().sendAdminChat(player, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            plugin.getDiscordImpl().sendAdminChat(null, format
                    .replace("%player%", "Console")
            );
        }
    }

    public void toggleStaffChat(ProxiedPlayer player) {
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

    public void toggleAdminChat(ProxiedPlayer player) {
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

    public void toggleDevChat(ProxiedPlayer player) {
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

    public void toggleAllChat(ProxiedPlayer player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            devChatPlayers.remove(player.getUniqueId());
            adminChatPlayers.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().ALLCHAT_TOGGLE_ON);
        }
    }

    public void hideStaffChat(ProxiedPlayer player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().STAFFCHAT_MUTED_OFF);
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
            }
            staffChatMuted.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().STAFFCHAT_MUTED_ON);
        }
    }

    public void hideAdminChat(ProxiedPlayer player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().ADMINCHAT_MUTED_OFF);
        } else {
            if (staffChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                staffChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
            }
            adminChatMuted.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().ADMINCHAT_MUTED_ON);
        }
    }

    public void hideDevChat(ProxiedPlayer player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().DEVCHAT_MUTED_OFF);
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }
            devChatMuted.add(player.getUniqueId());
            plugin.getColor().sendMessage(player, plugin.getConfig().DEVCHAT_MUTED_ON);
        }
    }
}