/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.utilities.chat;

import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.apache.commons.lang.StringEscapeUtils;

public class Placeholders {

    public static String setPlaceholders(ProxiedPlayer player, String placeholder) {
        placeholder = placeholder.replace("%prefix%", Config.PREFIX);
        placeholder = placeholder.replace("%player%", player.getName());
        placeholder = placeholder.replace("%server%", player.getServer().getInfo().getName());
        placeholder = placeholder.replace("%displayname%", ChatColor.stripColor(player.getDisplayName()));
        placeholder = placeholder.replace("%arrow%", StringEscapeUtils.unescapeJava("\u00BB"));
        placeholder = placeholder.replace("%arrow_2%", StringEscapeUtils.unescapeJava("\u27A5"));
        placeholder = placeholder.replace("%star%", StringEscapeUtils.unescapeJava("\u2726"));
        placeholder = placeholder.replace("%circle%", StringEscapeUtils.unescapeJava("\u2219"));
        placeholder = placeholder.replace("|", StringEscapeUtils.unescapeJava("\u2503"));

        return Color.translate(placeholder);
    }
}