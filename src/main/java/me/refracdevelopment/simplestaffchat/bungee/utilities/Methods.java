package me.refracdevelopment.simplestaffchat.bungee.utilities;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Methods {

    public static void sendStaffChat(CommandSender commandSender, String format) {
        if (!commandSender.hasPermission(Permissions.STAFFCHAT_COMMAND)) {
            Color.sendMessage(commandSender, Config.NO_PERMISSION);
            return;
        }

        for (ProxiedPlayer p : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(commandSender, format));
            }
        }
        Color.log2(Color.translate(commandSender, format));
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            BungeeStaffChat.getInstance().getDiscord().sendStaffChat(player, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            BungeeStaffChat.getInstance().getDiscord().sendStaffChat(null, format
                    .replace("%player%", "Console")
            );
        }
    }

    public static void sendDevChat(CommandSender commandSender, String format) {
        if (!commandSender.hasPermission(Permissions.DEVCHAT_COMMAND)) {
            Color.sendMessage(commandSender, Config.NO_PERMISSION);
            return;
        }

        for (ProxiedPlayer p : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(commandSender, format));
            }
        }
        Color.log2(Color.translate(commandSender, format));
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            BungeeStaffChat.getInstance().getDiscord().sendDevChat(player, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            BungeeStaffChat.getInstance().getDiscord().sendDevChat(null, format
                    .replace("%player%", "Console")
            );
        }
    }

    public static void sendAdminChat(CommandSender commandSender, String format) {
        if (!commandSender.hasPermission(Permissions.ADMINCHAT_COMMAND)) {
            Color.sendMessage(commandSender, Config.NO_PERMISSION);
            return;
        }

        for (ProxiedPlayer p : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(commandSender, format));
            }
        }
        Color.log2(Color.translate(commandSender, format));
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            BungeeStaffChat.getInstance().getDiscord().sendAdminChat(player, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            BungeeStaffChat.getInstance().getDiscord().sendAdminChat(null, format
                    .replace("%player%", "Console")
            );
        }
    }

    public static void toggleStaffChat(ProxiedPlayer player) {

        if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.NO_PERMISSION));
            return;
        }

        if (ToggleCommand.insc.contains(player.getUniqueId())) {
            ToggleCommand.insc.remove(player.getUniqueId());
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.STAFFCHAT_TOGGLE_OFF));
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }
            ToggleCommand.insc.add(player.getUniqueId());
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.STAFFCHAT_TOGGLE_ON));
        }
    }

    public static void toggleAdminChat(ProxiedPlayer player) {

        if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION);
            return;
        }

        if (AdminToggleCommand.inac.contains(player.getUniqueId())) {
            AdminToggleCommand.inac.remove(player.getUniqueId());
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.ADMINCHAT_TOGGLE_OFF));
        } else {
            if (DevToggleCommand.indc.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            AdminToggleCommand.inac.add(player.getUniqueId());
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.ADMINCHAT_TOGGLE_ON));
        }
    }

    public static void toggleDevChat(ProxiedPlayer player) {
        if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION);
            return;
        }

        if (DevToggleCommand.indc.contains(player.getUniqueId())) {
            DevToggleCommand.indc.remove(player.getUniqueId());
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.DEVCHAT_TOGGLE_OFF));
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            DevToggleCommand.indc.add(player.getUniqueId());
            Color.sendMessage(player, Placeholders.setPlaceholders(player, Config.DEVCHAT_TOGGLE_ON));
        }
    }

    public static void toggleAllChat(ProxiedPlayer player) {
        if (ToggleCommand.insc.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())
                || AdminToggleCommand.inac.contains(player.getUniqueId())) {
            ToggleCommand.insc.remove(player.getUniqueId());
            DevToggleCommand.indc.remove(player.getUniqueId());
            AdminToggleCommand.inac.remove(player.getUniqueId());
            Color.sendMessage(player, Config.ALLCHAT_TOGGLE_ON);
        }
    }
}