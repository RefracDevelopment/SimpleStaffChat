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

import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.entity.Player;

public class Placeholders {

    public static String setPlaceholders(Player player, String placeholder) {
        placeholder = placeholder.replace("%prefix%", Config.PREFIX);
        placeholder = placeholder.replace("%player%", player.getName());
        placeholder = placeholder.replace("%displayname%", player.getDisplayName());
        placeholder = placeholder.replace("%arrow%", StringEscapeUtils.unescapeJava("\u00BB"));
        placeholder = placeholder.replace("%arrow_2%", StringEscapeUtils.unescapeJava("\u27A5"));
        placeholder = placeholder.replace("%star%", StringEscapeUtils.unescapeJava("\u2726"));
        placeholder = placeholder.replace("%circle%", StringEscapeUtils.unescapeJava("\u2219"));
        placeholder = placeholder.replace("|", StringEscapeUtils.unescapeJava("\u2503"));

        return placeholder;
    }

    public static String setConsolePlaceholders(String placeholder) {
        placeholder = placeholder.replace("%prefix%", Config.PREFIX);
        placeholder = placeholder.replace("%player%", "Console");
        placeholder = placeholder.replace("%displayname%", "Console");
        placeholder = placeholder.replace("%arrow%", StringEscapeUtils.unescapeJava("\u00BB"));
        placeholder = placeholder.replace("%arrow_2%", StringEscapeUtils.unescapeJava("\u27A5"));
        placeholder = placeholder.replace("%star%", StringEscapeUtils.unescapeJava("\u2726"));
        placeholder = placeholder.replace("%circle%", StringEscapeUtils.unescapeJava("\u2219"));
        placeholder = placeholder.replace("|", StringEscapeUtils.unescapeJava("\u2503"));

        return placeholder;
    }
}