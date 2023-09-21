package me.refracdevelopment.simplestaffchat.velocity.config.cache;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

import java.util.List;

public enum Config {

    // General
    STAFFCHAT_SYMBOL("staffchat-symbol"),
    ADMINCHAT_SYMBOL("adminchat-symbol"),
    DEVCHAT_SYMBOL("devchat-symbol"),
    LUCKPERMS("luckperms"),
    STAFFCHAT_FORMAT("format.minecraft-format"),
    CONSOLE_STAFFCHAT_FORMAT("format.console-staffchat-format"),
    ADMINCHAT_FORMAT("format.adminchat-format"),
    CONSOLE_ADMINCHAT_FORMAT("format.console-adminchat-format"),
    DEVCHAT_FORMAT("format.devchat-format"),
    CONSOLE_DEVCHAT_FORMAT("format.console-devchat-format"),
    JOIN_ENABLED("join.enabled"),
    JOIN_FORMAT("join.join-format"),
    SWITCH_FORMAT("join.switch-format"),
    QUIT_FORMAT("join.quit-format"),
    PREFIX("messages.prefix"),
    NO_PERMISSION("messages.no-permission"),
    RELOAD("messages.reload"),
    STAFFCHAT_TOGGLE_ON("messages.toggle-on"),
    STAFFCHAT_TOGGLE_OFF("messages.toggle-off"),
    ADMINCHAT_TOGGLE_ON("messages.adminchat-toggle-on"),
    ADMINCHAT_TOGGLE_OFF("messages.adminchat-toggle-on"),
    DEVCHAT_TOGGLE_ON("messages.devchat-toggle-on"),
    DEVCHAT_TOGGLE_OFF("messages.devchat-toggle-on"),
    STAFFCHAT_OUTPUT("messages.staffchat-output"),
    STAFFCHAT_MESSAGE("messages.staffchat-message");

    private final String path;
    public static final VelocityStaffChat instance = VelocityStaffChat.getInstance();

    Config(String path) {
        this.path = path;
    }

    public String getString() {
        return instance.getConfigFile().getConfig().getString(path);
    }

    public List<String> getStringList() {
        return instance.getConfigFile().getConfig().getStringList(path);
    }

    public Boolean getBoolean() {
        return instance.getConfigFile().getConfig().getBoolean(path);
    }

}