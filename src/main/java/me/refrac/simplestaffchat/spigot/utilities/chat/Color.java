/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 RefracDevelopment
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package me.refrac.simplestaffchat.spigot.utilities.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public static void sendMessage(Player player, String source, boolean color, boolean placeholders) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;
        if (placeholders) source = Placeholders.setPlaceholders(player, source);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            source = PlaceholderAPI.setPlaceholders(player, source);
        }

        if (color) source = translate(source);

        player.sendMessage(source);
    }

    public static void sendMessage(CommandSender sender, String source, boolean color) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;
        if (color) source = translate(source);

        sender.sendMessage(source);
    }
}