package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler
    public void onStaffChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (ToggleCommand.insc.contains(player.getUniqueId()) && !event.isCommand()) {
            if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                ToggleCommand.insc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_OFF);
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && Config.SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.STAFFCHAT_SYMBOL, ""));

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        }
    }

    @EventHandler
    public void onAdminChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (AdminToggleCommand.inac.contains(player.getUniqueId()) && !event.isCommand()) {
            if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF);
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && Config.SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(Config.ADMINCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (DevToggleCommand.indc.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.ADMINCHAT_SYMBOL, ""));

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        }
    }

    @EventHandler
    public void onDevChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (DevToggleCommand.indc.contains(player.getUniqueId()) && !event.isCommand()) {
            if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.DEVCHAT_TOGGLE_OFF);
                return;
            }

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && Config.SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(Config.DEVCHAT_SYMBOL)) return;

            event.setCancelled(true);

            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.DEVCHAT_SYMBOL, ""));

            ProxyServer.getInstance().getPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        }
    }
}