package me.refracdevelopment.simplestaffchat.bungee.utilities;

import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Methods {

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
}