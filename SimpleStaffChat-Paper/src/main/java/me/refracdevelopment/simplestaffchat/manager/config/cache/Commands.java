package me.refracdevelopment.simplestaffchat.manager.config.cache;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;

import java.util.List;

public class Commands {

    public boolean STAFFCHAT_COMMAND_ENABLED;
    public String STAFFCHAT_COMMAND_PERMISSION;
    public List<String> STAFFCHAT_COMMAND_ALIASES;

    public boolean STAFF_TOGGLE_COMMAND_ENABLED;
    public String STAFF_TOGGLE_COMMAND_PERMISSION;
    public List<String> STAFF_TOGGLE_COMMAND_ALIASES;

    public boolean ADMINCHAT_COMMAND_ENABLED;
    public String ADMINCHAT_COMMAND_PERMISSION;
    public List<String> ADMINCHAT_COMMAND_ALIASES;

    public boolean ADMIN_TOGGLE_COMMAND_ENABLED;
    public String ADMIN_TOGGLE_COMMAND_PERMISSION;
    public List<String> ADMIN_TOGGLE_COMMAND_ALIASES;

    public boolean DEVCHAT_COMMAND_ENABLED;
    public String DEVCHAT_COMMAND_PERMISSION;
    public List<String> DEVCHAT_COMMAND_ALIASES;

    public boolean DEV_TOGGLE_COMMAND_ENABLED;
    public String DEV_TOGGLE_COMMAND_PERMISSION;
    public List<String> DEV_TOGGLE_COMMAND_ALIASES;

    public boolean CHAT_COMMAND_ENABLED;
    public String CHAT_COMMAND_PERMISSION;
    public List<String> CHAT_COMMAND_ALIASES;

    public boolean HIDE_COMMAND_ENABLED;
    public String HIDE_COMMAND_PERMISSION;
    public List<String> HIDE_COMMAND_ALIASES;

    public Commands() {
        loadConfig();
    }

    public void loadConfig() {
        STAFFCHAT_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.staffchat.enabled");
        STAFFCHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.staffchat.permission");
        STAFFCHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.staffchat.aliases");

        STAFF_TOGGLE_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.toggle.enabled");
        STAFF_TOGGLE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.toggle.permission");
        STAFF_TOGGLE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.toggle.aliases");

        ADMINCHAT_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.adminchat.enabled");
        ADMINCHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.adminchat.permission");
        ADMINCHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.adminchat.aliases");

        ADMIN_TOGGLE_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.toggle.enabled");
        ADMIN_TOGGLE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.admin-toggle.permission");
        ADMIN_TOGGLE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.admin-toggle.aliases");

        DEVCHAT_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.devchat.enabled");
        DEVCHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.devchat.permission");
        DEVCHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.devchat.aliases");

        DEV_TOGGLE_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.dev-toggle.enabled");
        DEV_TOGGLE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.dev-toggle.permission");
        DEV_TOGGLE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.dev-toggle.aliases");

        CHAT_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.chat.enabled");
        CHAT_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.chat.permission");
        CHAT_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.chat.aliases");

        HIDE_COMMAND_ENABLED = SimpleStaffChat.getInstance().getCommandsFile().getBoolean("commands.hide.enabled");
        HIDE_COMMAND_PERMISSION = SimpleStaffChat.getInstance().getCommandsFile().getString("commands.hide.permission");
        HIDE_COMMAND_ALIASES = SimpleStaffChat.getInstance().getCommandsFile().getStringList("commands.hide.aliases");
    }
}