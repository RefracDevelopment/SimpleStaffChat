package me.refracdevelopment.simplestaffchat.spigot.utilities.chat;

import dev.rosewood.rosegarden.utils.HexUtils;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import org.bukkit.Bukkit;

public class Color {

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