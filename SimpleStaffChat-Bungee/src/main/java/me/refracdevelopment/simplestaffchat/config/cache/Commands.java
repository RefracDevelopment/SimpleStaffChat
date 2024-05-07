package me.refracdevelopment.simplestaffchat.config.cache;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;

import java.util.List;

public class Commands {

    public String STAFFCHAT_COMMAND_PERMISSION;
    public List<String> STAFFCHAT_COMMAND_ALIASES;

    public String STAFF_TOGGLE_COMMAND_PERMISSION;
    public List<String> STAFF_TOGGLE_COMMAND_ALIASES;

    public String ADMINCHAT_COMMAND_PERMISSION;
    public List<String> ADMINCHAT_COMMAND_ALIASES;

    public String ADMIN_TOGGLE_COMMAND_PERMISSION;
    public List<String> ADMIN_TOGGLE_COMMAND_ALIASES;

    public String DEVCHAT_COMMAND_PERMISSION;
    public List<String> DEVCHAT_COMMAND_ALIASES;

    public String DEV_TOGGLE_COMMAND_PERMISSION;
    public List<String> DEV_TOGGLE_COMMAND_ALIASES;

    public String CHAT_COMMAND_PERMISSION;
    public List<String> CHAT_COMMAND_ALIASES;

    public String HIDE_COMMAND_PERMISSION;
    public List<String> HIDE_COMMAND_ALIASES;

    public Commands() {
        loadConfig();
    }

    public void loadConfig() {
        STAFFCHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.staffchat.permission");
        STAFFCHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.staffchat.aliases");

        STAFF_TOGGLE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.toggle.permission");
        STAFF_TOGGLE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.toggle.aliases");

        ADMINCHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.adminchat.permission");
        ADMINCHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.adminchat.aliases");

        ADMIN_TOGGLE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.admin-toggle.permission");
        ADMIN_TOGGLE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.admin-toggle.aliases");

        DEVCHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.devchat.permission");
        DEVCHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.devchat.aliases");

        DEV_TOGGLE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.dev-toggle.permission");
        DEV_TOGGLE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.dev-toggle.aliases");

        CHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.chat.permission");
        CHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.chat.aliases");

        HIDE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.hide.permission");
        HIDE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.hide.aliases");
    }
}