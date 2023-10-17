package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
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

        if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, "toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().STAFFCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            plugin.getMethods().sendStaffChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getSettings().STAFFCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().STAFFCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getSettings().STAFFCHAT_SYMBOL, "");
            String format = plugin.getSettings().STAFFCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            plugin.getMethods().sendStaffChat(player, format);
        }
    }

    @EventHandler
    public void onAdminChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, "adminchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().ADMINCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            plugin.getMethods().sendAdminChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getSettings().ADMINCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().ADMINCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().ADMINCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getSettings().ADMINCHAT_SYMBOL, "");
            String format = plugin.getSettings().ADMINCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            plugin.getMethods().sendAdminChat(player, format);
        }
    }

    @EventHandler
    public void onDevChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, "devchat-toggle-off");
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().DEVCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            plugin.getMethods().sendDevChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getSettings().DEVCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().DEVCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().DEVCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getSettings().DEVCHAT_SYMBOL, "");
            String format = plugin.getSettings().DEVCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%message%", message);

            plugin.getMethods().sendDevChat(player, format);
        }
    }
}