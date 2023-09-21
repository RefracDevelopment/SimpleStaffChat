package me.refracdevelopment.simplestaffchat.velocity.config.cache;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

import java.util.List;

public enum Commands {

    STAFFCHAT_COMMAND_ENABLED("commands.staffchat.enabled"),
    STAFFCHAT_ALIASES("commands.staffchat.aliases"),

    TOGGLE_COMMAND_ENABLED("commands.toggle.enabled"),
    TOGGLE_ALIASES("commands.toggle.aliases"),

    RELOAD_COMMAND_ENABLED("commands.reload.enabled"),
    RELOAD_ALIASES("commands.reload.aliases"),

    ADMINCHAT_COMMAND_ENABLED("commands.adminchat.enabled"),
    ADMINCHAT_ALIASES("commands.adminchat.aliases"),

    ADMIN_TOGGLE_COMMAND_ENABLED("commands.admin-toggle.enabled"),
    ADMIN_TOGGLE_ALIASES("commands.admin-toggle.aliases"),

    DEVCHAT_COMMAND_ENABLED("commands.devchat.enabled"),
    DEVCHAT_ALIASES("commands.devchat.aliases"),

    DEV_TOGGLE_COMMAND_ENABLED("commands.dev-toggle.enabled"),
    DEV_TOGGLE_ALIASES("commands.dev-toggle.aliases");

    private final String path;
    public static final VelocityStaffChat instance = VelocityStaffChat.getInstance();

    Commands(String path) {
        this.path = path;
    }

    public String getString() {
        return instance.getCommandsFile().getString(path);
    }

    public List<String> getStringList() {
        return instance.getCommandsFile().getConfig().getStringList(path);
    }

    public Boolean getBoolean() {
        return instance.getCommandsFile().getConfig().getBoolean(path);
    }
}