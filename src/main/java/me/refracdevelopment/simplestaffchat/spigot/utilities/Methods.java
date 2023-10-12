package me.refracdevelopment.simplestaffchat.spigot.utilities;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Methods {

    public static void sendStaffChat(CommandSender sender, String format) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!sender.hasPermission(Permissions.STAFFCHAT_COMMAND)) {
            locale.sendMessage(sender, "no-permission");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                locale.sendCustomMessage(p, Color.translate(sender, format));
            }
        }

        if (Config.BUNGEECORD && sender instanceof Player) {
            Player player = (Player) sender;
            SimpleStaffChat.getInstance().getPluginMessage().sendStaffChat(player, Color.translate(sender, format));
        }
        Color.log2(Color.translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SimpleStaffChat.getInstance().getDiscord().sendStaffChat(player, format
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "\u00BB")
            );
        } else {
            SimpleStaffChat.getInstance().getDiscord().sendStaffChat(null, format
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "\u00BB")
            );
        }
    }

    public static void sendDevChat(CommandSender sender, String format) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!sender.hasPermission(Permissions.DEVCHAT_COMMAND)) {
            locale.sendMessage(sender, "no-permission");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                locale.sendCustomMessage(p, Color.translate(sender, format));
            }
        }

        if (Config.BUNGEECORD && sender instanceof Player) {
            Player player = (Player) sender;
            SimpleStaffChat.getInstance().getPluginMessage().sendDevChat(player, Color.translate(sender, format));
        }
        Color.log2(Color.translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SimpleStaffChat.getInstance().getDiscord().sendDevChat(player, format
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "\u00BB")
            );
        } else {
            SimpleStaffChat.getInstance().getDiscord().sendDevChat(null, format
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "\u00BB")
            );
        }
    }

    public static void sendAdminChat(CommandSender sender, String format) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!sender.hasPermission(Permissions.ADMINCHAT_COMMAND)) {
            locale.sendMessage(sender, "no-permission");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                locale.sendCustomMessage(p, Color.translate(sender, format));
            }
        }

        if (Config.BUNGEECORD && sender instanceof Player) {
            Player player = (Player) sender;
            SimpleStaffChat.getInstance().getPluginMessage().sendDevChat(player, Color.translate(sender, format));
        }
        Color.log2(Color.translate(sender, format));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SimpleStaffChat.getInstance().getDiscord().sendAdminChat(player, format
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", player.getName())
                    .replace("%displayname%", ChatColor.stripColor(player.getDisplayName()))
                    .replace("%arrow%", "\u00BB")
            );
        } else {
            SimpleStaffChat.getInstance().getDiscord().sendAdminChat(null, format
                    .replace("%server%", Config.SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%arrow%", "\u00BB")
            );
        }
    }

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
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (ToggleCommand.insc.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())
                || AdminToggleCommand.inac.contains(player.getUniqueId())) {
            ToggleCommand.insc.remove(player.getUniqueId());
            DevToggleCommand.indc.remove(player.getUniqueId());
            AdminToggleCommand.inac.remove(player.getUniqueId());
            locale.sendMessage(player, "allchat-toggle-on");
        }
    }
}