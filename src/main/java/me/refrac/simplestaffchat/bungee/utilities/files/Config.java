/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 RefracDevelopment
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
package me.refrac.simplestaffchat.bungee.utilities.files;

import java.util.List;

public class Config {
    // General
    public static String STAFFCHAT_SYMBOL;
    public static String STAFFCHAT_FORMAT;
    public static String CONSOLE_FORMAT;

    // Staff Joins
    public static boolean JOIN_ENABLED;
    public static String JOIN_FORMAT;
    public static String SWITCH_FORMAT;
    public static String QUIT_FORMAT;

    // Messages
    public static String PREFIX;
    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String TOGGLE_ON;
    public static String TOGGLE_OFF;
    public static String STAFFCHAT_OUTPUT;
    public static List<String> STAFFCHAT_MESSAGE;

    // Commands
    public static boolean STAFFCHAT_ENABLED;
    public static String STAFFCHAT_COMMAND;
    public static String STAFFCHAT_ALIAS;
    public static boolean TOGGLE_ENABLED;
    public static String TOGGLE_COMMAND;
    public static String TOGGLE_ALIAS;
    public static boolean RELOAD_ENABLED;
    public static String RELOAD_COMMAND;
    public static String RELOAD_ALIAS;

    public static void loadConfig() {
        // General
        STAFFCHAT_SYMBOL = Files.getConfig().getString("staffchat-symbol");
        STAFFCHAT_FORMAT = Files.getConfig().getString("format.minecraft-format");
        CONSOLE_FORMAT = Files.getConfig().getString("format.console-staffchat-format");

        // Staff Joins
        JOIN_ENABLED = Files.getConfig().getBoolean("join.enabled");
        JOIN_FORMAT = Files.getConfig().getString("join.join-format");
        SWITCH_FORMAT = Files.getConfig().getString("join.switch-format");
        QUIT_FORMAT = Files.getConfig().getString("join.quit-format");

        // Messages
        PREFIX = Files.getConfig().getString("messages.prefix");
        NO_PERMISSION = Files.getConfig().getString("messages.no-permission");
        RELOAD = Files.getConfig().getString("messages.reload");
        TOGGLE_ON = Files.getConfig().getString("messages.toggle-on");
        TOGGLE_OFF = Files.getConfig().getString("messages.toggle-off");
        STAFFCHAT_OUTPUT = Files.getConfig().getString("messages.staffchat-output");
        STAFFCHAT_MESSAGE = Files.getConfig().getStringList("messages.staffchat-message");

        // Commands
        STAFFCHAT_ENABLED = Files.getConfig().getBoolean("commands.staffchat.enabled");
        STAFFCHAT_COMMAND = Files.getConfig().getString("commands.staffchat.command");
        STAFFCHAT_ALIAS = Files.getConfig().getString("commands.staffchat.alias");
        TOGGLE_ENABLED = Files.getConfig().getBoolean("commands.toggle.enabled");
        TOGGLE_COMMAND = Files.getConfig().getString("commands.toggle.command");
        TOGGLE_ALIAS = Files.getConfig().getString("commands.toggle.alias");
        RELOAD_ENABLED = Files.getConfig().getBoolean("commands.reload.enabled");
        RELOAD_COMMAND = Files.getConfig().getString("commands.reload.command");
        RELOAD_ALIAS = Files.getConfig().getString("commands.reload.alias");
    }
}