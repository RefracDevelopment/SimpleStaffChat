/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee.utilities.chat;

import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Placeholders {

    public static String setPlaceholders(ProxiedPlayer player, String placeholder) {
        placeholder = placeholder.replace("%prefix%", Config.PREFIX);
        placeholder = placeholder.replace("%player%", player.getName());
        placeholder = placeholder.replace("%displayname%", player.getDisplayName());
        placeholder = placeholder.replace("%arrow%", "\u00BB");
        placeholder = placeholder.replace("%arrow_2%", "\u27A5");
        placeholder = placeholder.replace("%star%", "\u2726");
        placeholder = placeholder.replace("%circle%", "\u2219");
        placeholder = placeholder.replace("|", "\u2503");

        return placeholder;
    }
}