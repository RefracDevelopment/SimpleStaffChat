/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.utilities.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class Color {

    public static TextComponent translate(ProxiedPlayer player, String source) {
        source = Placeholders.setPlaceholders(player, source);

        return format(source);
    }

    public static String translate(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static TextComponent format(String source) {
        return new TextComponent(translate(source));
    }

    public static List<String> translate(List<String> source) {
        return source.stream().map(Color::translate).collect(Collectors.toList());
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