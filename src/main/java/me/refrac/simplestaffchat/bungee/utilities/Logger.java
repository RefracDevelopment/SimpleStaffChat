/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.utilities;

import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.ProxyServer;

import java.util.logging.Level;

public enum Logger {

    NONE('r'), SUCCESS('a'), ERROR('c'), WARNING('e'), INFO('b');

    char color;

    Logger(char color) { this.color = color; }

    public void out(String message) {
        message = Color.translate(String.format("&%c%s", this.color, message));
        ProxyServer.getInstance().getLogger().log(Level.ALL, message);
    }
}
