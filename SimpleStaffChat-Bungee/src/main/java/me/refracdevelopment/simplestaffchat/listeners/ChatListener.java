package me.refracdevelopment.simplestaffchat.listeners;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatListener implements Listener {

    private final SimpleStaffChat plugin;

    public ChatListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onStaffChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand())
            return;

        if (Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);

            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                RyMessageUtils.sendPluginMessage(player, "staffchat-toggle-off");
                return;
            }

            String message = event.getMessage();
            String format = plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && plugin.getConfig().SYMBOLS_ENABLED) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().STAFFCHAT_SYMBOL)) return;

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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAdminChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand())
            return;

        if (Methods.getAdminChatPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);

            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                RyMessageUtils.sendPluginMessage(player, "adminchat-toggle-off");
                return;
            }

            String message = event.getMessage();
            String format = plugin.getConfig().ADMINCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && plugin.getConfig().SYMBOLS_ENABLED) {
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDevChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (event.isCommand())
            return;

        if (Methods.getDevChatPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);

            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                RyMessageUtils.sendPluginMessage(player, "devchat-toggle-off");
                return;
            }

            String message = event.getMessage();
            String format = plugin.getConfig().DEVCHAT_FORMAT
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && plugin.getConfig().SYMBOLS_ENABLED) {
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