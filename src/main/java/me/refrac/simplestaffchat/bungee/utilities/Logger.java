/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee.utilities;

import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public enum Logger {

    NONE('r'), SUCCESS('a'), ERROR('c'), WARNING('e'), INFO('b');

    char color;

    Logger(char color) { this.color = color; }

    public void out(String message) {
        message = Color.translate(String.format("&%c%s", this.color, message));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message));
    }
}
