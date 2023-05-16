package me.refracdevelopment.simplestaffchat.spigot.utilities;

import dev.rosewood.rosegarden.hook.PlaceholderAPIHook;
import dev.rosewood.rosegarden.utils.HexUtils;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Color {

    public static String translate(CommandSender sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        if (sender instanceof Player) {
            return PlaceholderAPIHook.applyPlaceholders((Player) sender, translate(source));
        } else return translate(source);
    }

    public static String translate(String source) {
        return HexUtils.colorify(source);
    }

    public static void log(String message) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        String prefix = locale.getLocaleMessage("prefix");

        locale.sendCustomMessage(Bukkit.getConsoleSender(), prefix + message);
    }

    // Used for console StaffChat messages
    public static void log2(String message) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        locale.sendCustomMessage(Bukkit.getConsoleSender(), message);
    }
}