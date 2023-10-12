package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;

public class Methods {

    public static void sendStaffChat(CommandSource commandSource, String format, String message) {
        if (!commandSource.hasPermission(Permissions.STAFFCHAT_COMMAND)) {
            Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
            return;
        }

        for (Player p : VelocityStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(commandSource, format));
            }
        }
        Color.log2(Color.translate(commandSource, format));
        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;
            VelocityStaffChat.getInstance().getDiscord().sendStaffChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            VelocityStaffChat.getInstance().getDiscord().sendStaffChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public static void sendDevChat(CommandSource commandSource, String format, String message) {
        if (!commandSource.hasPermission(Permissions.DEVCHAT_COMMAND)) {
            Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
            return;
        }

        for (Player p : VelocityStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(commandSource, format));
            }
        }
        Color.log2(Color.translate(commandSource, format));
        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;
            VelocityStaffChat.getInstance().getDiscord().sendDevChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            VelocityStaffChat.getInstance().getDiscord().sendDevChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public static void sendAdminChat(CommandSource commandSource, String format, String message) {
        if (!commandSource.hasPermission(Permissions.ADMINCHAT_COMMAND)) {
            Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
            return;
        }

        for (Player p : VelocityStaffChat.getInstance().getServer().getAllPlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                Color.sendMessage(p, Color.translate(commandSource, format));
            }
        }
        Color.log2(Color.translate(commandSource, format));
        if (commandSource instanceof Player) {
            Player player = (Player) commandSource;
            VelocityStaffChat.getInstance().getDiscord().sendAdminChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            VelocityStaffChat.getInstance().getDiscord().sendAdminChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public static void toggleStaffChat(Player player) {
        if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION.getString());
            return;
        }

        if (ToggleCommand.insc.contains(player.getUniqueId())) {
            ToggleCommand.insc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_OFF.getString());
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }
            ToggleCommand.insc.add(player.getUniqueId());
            Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_ON.getString());
        }
    }

    public static void toggleAdminChat(Player player) {
        if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION.getString());
            return;
        }

        if (AdminToggleCommand.inac.contains(player.getUniqueId())) {
            AdminToggleCommand.inac.remove(player.getUniqueId());
            Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF.getString());
        } else {
            if (DevToggleCommand.indc.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            AdminToggleCommand.inac.add(player.getUniqueId());
            Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_ON.getString());
        }
    }

    public static void toggleDevChat(Player player) {
        if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION.getString());
            return;
        }

        if (DevToggleCommand.indc.contains(player.getUniqueId())) {
            DevToggleCommand.indc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.DEVCHAT_TOGGLE_OFF.getString());
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            DevToggleCommand.indc.add(player.getUniqueId());
            Color.sendMessage(player, Config.DEVCHAT_TOGGLE_ON.getString());
        }
    }

    public static void toggleAllChat(Player player) {
        if (ToggleCommand.insc.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())
                || AdminToggleCommand.inac.contains(player.getUniqueId())) {
            ToggleCommand.insc.remove(player.getUniqueId());
            ToggleCommand.insc.remove(player.getUniqueId());
            ToggleCommand.insc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.ALLCHAT_TOGGLE_ON.getString());
        }
    }
}