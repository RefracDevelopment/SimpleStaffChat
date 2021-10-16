package me.refrac.simplestaffchat.spigot.utilities.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Color {

    public static String translate(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static String translate(Player player, String source) {
        source = Placeholders.setPlaceholders(player, source);

        return Color.translate(source);
    }

}