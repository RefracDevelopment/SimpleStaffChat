package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;

import java.util.List;

public class Commands extends Manager {

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

    public boolean STAFF_HIDE_COMMAND_ENABLED;
    public String STAFF_HIDE_COMMAND_PERMISSION;
    public List<String> STAFF_HIDE_COMMAND_ALIASES;

    public boolean ADMIN_HIDE_COMMAND_ENABLED;
    public String ADMIN_HIDE_COMMAND_PERMISSION;
    public List<String> ADMIN_HIDE_COMMAND_ALIASES;

    public boolean DEV_HIDE_COMMAND_ENABLED;
    public String DEV_HIDE_COMMAND_PERMISSION;
    public List<String> DEV_HIDE_COMMAND_ALIASES;

    public Commands(SimpleStaffChat plugin) {
        super(plugin);
        loadConfig();
    }

    public void loadConfig() {
        STAFFCHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.staffchat.enabled");
        STAFFCHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.staffchat.permission");
        STAFFCHAT_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.staffchat.aliases");

        STAFF_TOGGLE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.toggle.enabled");
        STAFF_TOGGLE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.toggle.permission");
        STAFF_TOGGLE_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.toggle.aliases");

        ADMINCHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.adminchat.enabled");
        ADMINCHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.adminchat.permission");
        ADMINCHAT_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.adminchat.aliases");

        ADMIN_TOGGLE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.toggle.enabled");
        ADMIN_TOGGLE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.admin-toggle.permission");
        ADMIN_TOGGLE_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.admin-toggle.aliases");

        DEVCHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.devchat.enabled");
        DEVCHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.devchat.permission");
        DEVCHAT_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.devchat.aliases");

        DEV_TOGGLE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.dev-toggle.enabled");
        DEV_TOGGLE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.dev-toggle.permission");
        DEV_TOGGLE_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.dev-toggle.aliases");

        CHAT_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.chat.enabled");
        CHAT_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.chat.permission");
        CHAT_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.chat.aliases");

        STAFF_HIDE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.hide.enabled");
        STAFF_HIDE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.hide.permission");
        STAFF_HIDE_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.hide.aliases");

        ADMIN_HIDE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.admin-hide.enabled");
        ADMIN_HIDE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.admin-hide.permission");
        ADMIN_HIDE_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.admin-hide.aliases");

        DEV_HIDE_COMMAND_ENABLED = plugin.getCommandsFile().getBoolean("commands.dev-hide.enabled");
        DEV_HIDE_COMMAND_PERMISSION = plugin.getCommandsFile().getString("commands.dev-hide.permission");
        DEV_HIDE_COMMAND_ALIASES = plugin.getCommandsFile().getStringList("commands.dev-hide.aliases");
    }
}