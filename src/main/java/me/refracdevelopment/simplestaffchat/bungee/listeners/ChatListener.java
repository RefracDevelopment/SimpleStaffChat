package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
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

        if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId()) && !event.isCommand()) {
            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, plugin.getConfig().STAFFCHAT_TOGGLE_OFF);
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendStaffChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getConfig().STAFFCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().STAFFCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = plugin.getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(plugin.getConfig().STAFFCHAT_SYMBOL, ""));

            plugin.getMethods().sendStaffChat(player, format);
        }
    }

    @EventHandler
    public void onAdminChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) && !event.isCommand()) {
            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, plugin.getConfig().ADMINCHAT_TOGGLE_OFF);
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendAdminChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getConfig().ADMINCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().ADMINCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().ADMINCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = plugin.getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(plugin.getConfig().ADMINCHAT_SYMBOL, ""));

            plugin.getMethods().sendAdminChat(player, format);
        }
    }

    @EventHandler
    public void onDevChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId()) && !event.isCommand()) {
            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, plugin.getConfig().DEVCHAT_TOGGLE_OFF);
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getConfig().DEVCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendDevChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getConfig().DEVCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().DEVCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().DEVCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = plugin.getConfig().DEVCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(plugin.getConfig().DEVCHAT_SYMBOL, ""));

            plugin.getMethods().sendDevChat(player, format);
        }
    }
}