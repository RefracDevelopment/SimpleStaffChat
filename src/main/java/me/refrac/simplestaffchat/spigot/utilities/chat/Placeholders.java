/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.utilities.chat;

import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Placeholders {

    public static String setPlaceholders(Player player, String placeholder) {
        placeholder = placeholder.replace("%prefix%", Config.PREFIX);
        placeholder = placeholder.replace("%player%", player.getName());
        placeholder = placeholder.replace("%displayname%", ChatColor.stripColor(player.getDisplayName()));
        placeholder = placeholder.replace("%arrow%", StringEscapeUtils.unescapeJava("\u00BB"));
        placeholder = placeholder.replace("%arrow_2%", StringEscapeUtils.unescapeJava("\u27A5"));
        placeholder = placeholder.replace("%star%", StringEscapeUtils.unescapeJava("\u2726"));
        placeholder = placeholder.replace("%circle%", StringEscapeUtils.unescapeJava("\u2219"));
        placeholder = placeholder.replace("|", StringEscapeUtils.unescapeJava("\u2503"));

        return Color.translate(placeholder);
    }
}