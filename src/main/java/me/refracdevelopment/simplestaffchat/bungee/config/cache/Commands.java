package me.refracdevelopment.simplestaffchat.bungee.config.cache;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Manager;

public class Commands extends Manager {

    public boolean STAFFCHAT_COMMAND_ENABLED;
    public String STAFFCHAT_COMMAND;
    public String STAFFCHAT_COMMAND_PERMISSION;
    public String STAFFCHAT_COMMAND_ALIAS;

    public boolean STAFF_TOGGLE_COMMAND_ENABLED;
    public String STAFF_TOGGLE_COMMAND;
    public String STAFF_TOGGLE_COMMAND_PERMISSION;
    public String STAFF_TOGGLE_COMMAND_ALIAS;

    public boolean ADMINCHAT_COMMAND_ENABLED;
    public String ADMINCHAT_COMMAND;
    public String ADMINCHAT_COMMAND_PERMISSION;
    public String ADMINCHAT_COMMAND_ALIAS;

    public boolean ADMIN_TOGGLE_COMMAND_ENABLED;
    public String ADMIN_TOGGLE_COMMAND;
    public String ADMIN_TOGGLE_COMMAND_PERMISSION;
    public String ADMIN_TOGGLE_COMMAND_ALIAS;

    public boolean DEVCHAT_COMMAND_ENABLED;
    public String DEVCHAT_COMMAND;
    public String DEVCHAT_COMMAND_PERMISSION;
    public String DEVCHAT_COMMAND_ALIAS;

    public boolean DEV_TOGGLE_COMMAND_ENABLED;
    public String DEV_TOGGLE_COMMAND;
    public String DEV_TOGGLE_COMMAND_PERMISSION;
    public String DEV_TOGGLE_COMMAND_ALIAS;

    public boolean CHAT_COMMAND_ENABLED;
    public String CHAT_COMMAND;
    public String CHAT_COMMAND_PERMISSION;
    public String CHAT_COMMAND_ALIAS;

    public boolean STAFF_HIDE_COMMAND_ENABLED;
    public String STAFF_HIDE_COMMAND;
    public String STAFF_HIDE_COMMAND_PERMISSION;
    public String STAFF_HIDE_COMMAND_ALIAS;

    public boolean ADMIN_HIDE_COMMAND_ENABLED;
    public String ADMIN_HIDE_COMMAND;
    public String ADMIN_HIDE_COMMAND_PERMISSION;
    public String ADMIN_HIDE_COMMAND_ALIAS;

    public boolean DEV_HIDE_COMMAND_ENABLED;
    public String DEV_HIDE_COMMAND;
    public String DEV_HIDE_COMMAND_PERMISSION;
    public String DEV_HIDE_COMMAND_ALIAS;

    public Commands(BungeeStaffChat plugin) {
        super(plugin);
    }

    public void loadConfig() {
        STAFFCHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.staffchat.enabled");
        STAFFCHAT_COMMAND = plugin.getCommandsFile().getString("commands.staffchat.command");
        STAFFCHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.staffchat.permission");
        STAFFCHAT_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.staffchat.alias");

        STAFF_TOGGLE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.toggle.enabled");
        STAFF_TOGGLE_COMMAND = plugin.getCommandsFile().getString("commands.toggle.command");
        STAFF_TOGGLE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.toggle.permission");
        STAFF_TOGGLE_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.toggle.alias");

        ADMINCHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.adminchat.enabled");
        ADMINCHAT_COMMAND = plugin.getCommandsFile().getString("commands.adminchat.command");
        ADMINCHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.adminchat.permission");
        ADMINCHAT_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.adminchat.alias");

        ADMIN_TOGGLE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.toggle.enabled");
        ADMIN_TOGGLE_COMMAND = plugin.getCommandsFile().getString("commands.admin-toggle.command");
        ADMIN_TOGGLE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.admin-toggle.permission");
        ADMIN_TOGGLE_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.admin-toggle.alias");

        DEVCHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.devchat.enabled");
        DEVCHAT_COMMAND = plugin.getCommandsFile().getString("commands.devchat.command");
        DEVCHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.devchat.permission");
        DEVCHAT_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.devchat.alias");

        DEV_TOGGLE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.dev-toggle.enabled");
        DEV_TOGGLE_COMMAND = plugin.getCommandsFile().getString("commands.dev-toggle.command");
        DEV_TOGGLE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.dev-toggle.permission");
        DEV_TOGGLE_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.dev-toggle.alias");

        CHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.chat.enabled");
        CHAT_COMMAND = plugin.getCommandsFile().getString("commands.chat.command");
        CHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.chat.permission");
        CHAT_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.chat.alias");

        STAFF_HIDE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.hide.enabled");
        STAFF_HIDE_COMMAND = plugin.getCommandsFile().getString("commands.hide.command");
        STAFF_HIDE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.hide.permission");
        STAFF_HIDE_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.hide.alias");

        ADMIN_HIDE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.admin-hide.enabled");
        ADMIN_HIDE_COMMAND = plugin.getCommandsFile().getString("commands.admin-hide.command");
        ADMIN_HIDE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.admin-hide.permission");
        ADMIN_HIDE_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.admin-hide.alias");

        DEV_HIDE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.dev-hide.enabled");
        DEV_HIDE_COMMAND = plugin.getCommandsFile().getString("commands.dev-hide.command");
        DEV_HIDE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.dev-hide.permission");
        DEV_HIDE_COMMAND_ALIAS = plugin.getCommandsFile().getString("commands.dev-hide.alias");
    }
}