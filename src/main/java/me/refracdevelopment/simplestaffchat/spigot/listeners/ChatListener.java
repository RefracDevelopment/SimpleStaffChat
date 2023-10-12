package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
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

        if (ToggleCommand.insc.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                ToggleCommand.insc.remove(player.getUniqueId());
                locale.sendMessage(player, "toggle-off", Placeholders.setPlaceholders(player));
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.MINECRAFT_FORMAT.replace("%message%", message);

            Methods.sendStaffChat(player, format);
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && Config.SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.MINECRAFT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.STAFFCHAT_SYMBOL, ""));

            Methods.sendStaffChat(player, format);
        }
    }

    @EventHandler
    public void onAdminChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (AdminToggleCommand.inac.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                locale.sendMessage(player, "adminchat-toggle-off", Placeholders.setPlaceholders(player));
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.replace("%message%", message);

            Methods.sendAdminChat(player, format);
        } else if (event.getMessage().startsWith(Config.ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && Config.SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(Config.ADMINCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (DevToggleCommand.indc.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.ADMINCHAT_SYMBOL, ""));

            Methods.sendAdminChat(player, format);
        }
    }

    @EventHandler
    public void onDevChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (DevToggleCommand.indc.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                locale.sendMessage(player, "devchat-toggle-off", Placeholders.setPlaceholders(player));
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.replace("%message%", message);

            Methods.sendDevChat(player, format);
        } else if (event.getMessage().startsWith(Config.DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && Config.SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(Config.DEVCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.DEVCHAT_SYMBOL, ""));

            Methods.sendDevChat(player, format);
        }
    }
}