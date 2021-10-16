package me.refrac.simplestaffchat.spigot.utilities.chat;

import org.bukkit.entity.Player;

public class Placeholders {

    public static String setPlaceholders(Player player, String placeholder) {
        placeholder = placeholder.replace("%player%", player.getName());

        return Color.translate(placeholder);
    }
}