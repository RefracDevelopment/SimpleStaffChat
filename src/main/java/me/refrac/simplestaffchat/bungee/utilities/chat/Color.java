/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee.utilities.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Color {

    public static TextComponent translate(ProxiedPlayer player, String source) {
        source = Placeholders.setPlaceholders(player, source);

        return new TextComponent(translate(source));
    }

    public static String translate(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static TextComponent format(String source) {
        return new TextComponent(translate(source));
    }

    public static void sendMessage(ProxiedPlayer player, String source, boolean color, boolean placeholders) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;
        if (placeholders) source = Placeholders.setPlaceholders(player, source);

        if (color) source = translate(source);

        player.sendMessage(new TextComponent(source));
    }

    public static void sendMessage(CommandSender player, String source, boolean color) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;
        if (color) source = translate(source);

        player.sendMessage(new TextComponent(source));
    }
}