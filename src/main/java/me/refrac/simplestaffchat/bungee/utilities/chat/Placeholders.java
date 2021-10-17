/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.utilities.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Placeholders {

    public static String setPlaceholders(ProxiedPlayer player, String placeholder) {
        placeholder = placeholder.replace("%player%", player.getName());
        placeholder = placeholder.replace("%server%", player.getServer().getInfo().getName());
        placeholder = placeholder.replace("%displayname%", ChatColor.stripColor(player.getDisplayName()));

        return Color.translate(placeholder);
    }
}