package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
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

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(player, format));
                }
            });
            if (Config.BUNGEECORD) {
                plugin.getPluginMessage().sendStaffChat(player, Color.translate(player, format));
            }
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.MINECRAFT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.STAFFCHAT_SYMBOL, ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(player, format));
                }
            });
            if (Config.BUNGEECORD) {
                plugin.getPluginMessage().sendStaffChat(player, Color.translate(player, format));
            }
            Color.log2(Color.translate(player, format));
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

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(player, format));
                }
            });
            if (Config.BUNGEECORD) {
                plugin.getPluginMessage().sendAdminChat(player, Color.translate(player, format));
            }
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.ADMINCHAT_SYMBOL)) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.ADMINCHAT_SYMBOL, ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(player, format));
                }
            });
            if (Config.BUNGEECORD) {
                plugin.getPluginMessage().sendAdminChat(player, Color.translate(player, format));
            }
            Color.log2(Color.translate(player, format));
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

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(player, format));
                }
            });
            if (Config.BUNGEECORD) {
                plugin.getPluginMessage().sendDevChat(player, Color.translate(player, format));
            }
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.DEVCHAT_SYMBOL)) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.DEVCHAT_SYMBOL, ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(player, format));
                }
            });
            if (Config.BUNGEECORD) {
                plugin.getPluginMessage().sendDevChat(player, Color.translate(player, format));
            }
            Color.log2(Color.translate(player, format));
        }
    }
}