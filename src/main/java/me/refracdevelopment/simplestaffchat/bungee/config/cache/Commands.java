package me.refracdevelopment.simplestaffchat.bungee.config.cache;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;

public class Commands {

    public static boolean STAFFCHAT_COMMAND_ENABLED;
    public static String STAFFCHAT_COMMAND;
    public static String STAFFCHAT_ALIAS;

    public static boolean TOGGLE_COMMAND_ENABLED;
    public static String TOGGLE_COMMAND;
    public static String TOGGLE_ALIAS;

    public static boolean RELOAD_COMMAND_ENABLED;
    public static String RELOAD_COMMAND;
    public static String RELOAD_ALIAS;

    public static boolean ADMINCHAT_COMMAND_ENABLED;
    public static String ADMINCHAT_COMMAND;
    public static String ADMINCHAT_ALIAS;

    public static boolean ADMIN_TOGGLE_COMMAND_ENABLED;
    public static String ADMIN_TOGGLE_COMMAND;
    public static String ADMIN_TOGGLE_ALIAS;

    public static boolean DEVCHAT_COMMAND_ENABLED;
    public static String DEVCHAT_COMMAND;
    public static String DEVCHAT_ALIAS;

    public static boolean DEV_TOGGLE_COMMAND_ENABLED;
    public static String DEV_TOGGLE_COMMAND;
    public static String DEV_TOGGLE_ALIAS;

    public static boolean CHAT_COMMAND_ENABLED;
    public static String CHAT_COMMAND;
    public static String CHAT_ALIAS;

    public static void loadConfig() {
        STAFFCHAT_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.staffchat.enabled");
        STAFFCHAT_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.staffchat.command");
        STAFFCHAT_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.staffchat.alias");

        TOGGLE_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.toggle.enabled");
        TOGGLE_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.toggle.command");
        TOGGLE_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.toggle.alias");

        RELOAD_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.reload.enabled");
        RELOAD_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.reload.command");
        RELOAD_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.reload.alias");

        ADMINCHAT_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.adminchat.enabled");
        ADMINCHAT_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.adminchat.command");
        ADMINCHAT_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.adminchat.alias");

        ADMIN_TOGGLE_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.toggle.enabled");
        ADMIN_TOGGLE_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.admin-toggle.command");
        ADMIN_TOGGLE_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.admin-toggle.alias");

        DEVCHAT_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.devchat.enabled");
        DEVCHAT_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.devchat.command");
        DEVCHAT_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.devchat.alias");

        DEV_TOGGLE_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.dev-toggle.enabled");
        DEV_TOGGLE_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.dev-toggle.command");
        DEV_TOGGLE_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.dev-toggle.alias");

        CHAT_COMMAND_ENABLED = BungeeStaffChat.getInstance().getCommandsFile().getBoolean("commands.chat.enabled");
        CHAT_COMMAND = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.chat.command");
        CHAT_ALIAS = BungeeStaffChat.getInstance().getCommandsFile().getString("commands.chat.alias");
    }
}