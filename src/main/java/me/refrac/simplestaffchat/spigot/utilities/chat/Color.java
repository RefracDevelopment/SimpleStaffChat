/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.spigot.utilities.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Color {

    public static String translate(Player player, String source) {
        source = Placeholders.setPlaceholders(player, source);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setPlaceholders(player, Color.translate(source));
        } else return Color.translate(source);
    }

    public static String translate(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static List<String> translate(List<String> source) {
        return source.stream().map(Color::translate).collect(Collectors.toList());
    }

    public static List<String> translate(Player player, List<String> source) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setPlaceholders(player, Color.translate(source));
        } else return Color.translate(source);
    }

    public static void sendMessage(Player player, String source, boolean color, boolean placeholders) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;
        if (placeholders) source = Placeholders.setPlaceholders(player, source);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            source = PlaceholderAPI.setPlaceholders(player, source);
        }

        if (color) source = translate(source);

        player.sendMessage(source);
    }

    public static void sendMessage(CommandSender player, String source, boolean color) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;
        if (color) source = translate(source);

        player.sendMessage(source);
    }
}