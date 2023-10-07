package me.refracdevelopment.simplestaffchat.spigot.utilities;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import org.bukkit.entity.Player;

public class Methods {

    public static void toggleStaffChat(Player player) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
            locale.sendMessage(player, "no-permission", Placeholders.setPlaceholders(player));
            return;
        }

        if (ToggleCommand.insc.contains(player.getUniqueId())) {
            ToggleCommand.insc.remove(player.getUniqueId());
            locale.sendMessage(player, "toggle-off", Placeholders.setPlaceholders(player));
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }
            ToggleCommand.insc.add(player.getUniqueId());
            locale.sendMessage(player, "toggle-on", Placeholders.setPlaceholders(player));
        }
    }

    public static void toggleAdminChat(Player player) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
            locale.sendMessage(player, "no-permission");
            return;
        }

        if (AdminToggleCommand.inac.contains(player.getUniqueId())) {
            AdminToggleCommand.inac.remove(player.getUniqueId());
            locale.sendMessage(player, "adminchat-toggle-off", Placeholders.setPlaceholders(player));
        } else {
            if (DevToggleCommand.indc.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            AdminToggleCommand.inac.add(player.getUniqueId());
            locale.sendMessage(player, "adminchat-toggle-on", Placeholders.setPlaceholders(player));
        }
    }

    public static void toggleDevChat(Player player) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
            locale.sendMessage(player, "no-permission");
            return;
        }

        if (DevToggleCommand.indc.contains(player.getUniqueId())) {
            DevToggleCommand.indc.remove(player.getUniqueId());
            locale.sendMessage(player, "devchat-toggle-off", Placeholders.setPlaceholders(player));
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            DevToggleCommand.indc.add(player.getUniqueId());
            locale.sendMessage(player, "devchat-toggle-on", Placeholders.setPlaceholders(player));
        }
    }

    public static void toggleAllChat(Player player) {
        ToggleCommand.insc.remove(player);
        DevToggleCommand.indc.remove(player);
        AdminToggleCommand.inac.remove(player);
    }
}