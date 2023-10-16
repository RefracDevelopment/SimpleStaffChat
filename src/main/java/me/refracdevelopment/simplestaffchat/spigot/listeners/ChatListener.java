package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
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

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                locale.sendMessage(player, "toggle-off", plugin.getPlaceholders().setPlaceholders(player));
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().MINECRAFT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendStaffChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getSettings().STAFFCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().STAFFCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = plugin.getSettings().MINECRAFT_FORMAT.replace("%message%", message
                    .replaceFirst(plugin.getSettings().STAFFCHAT_SYMBOL, ""));

            plugin.getMethods().sendStaffChat(player, format);
        }
    }

    @EventHandler
    public void onAdminChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                locale.sendMessage(player, "adminchat-toggle-off", plugin.getPlaceholders().setPlaceholders(player));
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().ADMINCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendAdminChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getSettings().ADMINCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().ADMINCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().ADMINCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = plugin.getSettings().ADMINCHAT_FORMAT.replace("%message%", message
                    .replaceFirst(plugin.getSettings().ADMINCHAT_SYMBOL, ""));

            plugin.getMethods().sendAdminChat(player, format);
        }
    }

    @EventHandler
    public void onDevChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                locale.sendMessage(player, "devchat-toggle-off", plugin.getPlaceholders().setPlaceholders(player));
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = plugin.getSettings().DEVCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendDevChat(player, format);
        } else if (event.getMessage().startsWith(plugin.getSettings().DEVCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().DEVCHAT_SYMBOL) && plugin.getSettings().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getSettings().DEVCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = plugin.getSettings().DEVCHAT_FORMAT.replace("%message%", message
                    .replaceFirst(plugin.getSettings().DEVCHAT_SYMBOL, ""));

            plugin.getMethods().sendDevChat(player, format);
        }
    }
}