package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {
    
    private final BungeeStaffChat plugin;
    
    public ChatListener(BungeeStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStaffChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand())
            return;

        if (Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, "staffchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().STAFFCHAT_SYMBOL))
                return;

            event.setCancelled(true);

            if (Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getConfig().STAFFCHAT_SYMBOL, "");
            String format = plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        }
    }

    @EventHandler
    public void onAdminChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand())
            return;

        if (Methods.getAdminChatPlayers().contains(player.getUniqueId())) {
            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, "adminchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getConfig().ADMINCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().ADMINCHAT_SYMBOL))
                return;

            event.setCancelled(true);

            if (Methods.getDevChatPlayers().contains(player.getUniqueId()) || Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getConfig().ADMINCHAT_SYMBOL, "");
            String format = plugin.getConfig().ADMINCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        }
    }

    @EventHandler
    public void onDevChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand())
            return;

        if (Methods.getDevChatPlayers().contains(player.getUniqueId())) {
            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, "devchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getConfig().DEVCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().DEVCHAT_SYMBOL))
                return;

            event.setCancelled(true);

            if (Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getConfig().DEVCHAT_SYMBOL, "");
            String format = plugin.getConfig().DEVCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        }
    }
}