package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final SimpleStaffChat plugin;

    public ChatListener(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStaffChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/")) return;

        if (Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, "staffchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().STAFFCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getSettings().STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().STAFFCHAT_SYMBOL))
                return;

            event.setCancelled(true);

            if (Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getSettings().STAFFCHAT_SYMBOL, "");
            String format = plugin.getSettings().STAFFCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        }
    }

    @EventHandler
    public void onAdminChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;

        if (Methods.getAdminChatPlayers().contains(player.getUniqueId())) {
            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, "adminchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().ADMINCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getSettings().ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().ADMINCHAT_SYMBOL))
                return;

            event.setCancelled(true);

            if (Methods.getDevChatPlayers().contains(player.getUniqueId()) || Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getSettings().ADMINCHAT_SYMBOL, "");
            String format = plugin.getSettings().ADMINCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        }
    }

    @EventHandler
    public void onDevChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;

        if (Methods.getDevChatPlayers().contains(player.getUniqueId())) {
            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, "devchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().DEVCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getSettings().DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().DEVCHAT_SYMBOL))
                return;

            event.setCancelled(true);

            if (Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getSettings().DEVCHAT_SYMBOL, "");
            String format = plugin.getSettings().DEVCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        }
    }
}