package me.refracdevelopment.simplestaffchat.velocity.config.cache;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

import java.util.List;

public enum Discord {

    // Webhooks
    STAFFCHAT_WEBHOOK("webhook.staffchat"),
    DEVCHAT_WEBHOOK("webhook.devchat"),
    ADMINCHAT_WEBHOOK("webhook.adminchat"),
    JOIN_WEBHOOK("webhook.join"),
    // General
    DISCORD_ENABLED("discord.enabled"),
    DISCORD_EMBED("discord.embed-message"),
    DISCORD_TITLE("discord.title"),
    DISCORD_FOOTER("discord.footer"),
    DISCORD_FORMAT("discord.velocity.format"),
    DISCORD_JOIN_FORMAT("discord.velocity.join-format"),
    DISCORD_SWITCH_FORMAT("discord.velocity.switch-format"),
    DISCORD_LEAVE_FORMAT("discord.velocity.leave-format")
    ;

    private final String path;
    public static final VelocityStaffChat instance = VelocityStaffChat.getInstance();

    Discord(String path) {
        this.path = path;
    }

    public String getString() {
        return instance.getDiscordFile().getConfig().getString(path);
    }

    public List<String> getStringList() {
        return instance.getDiscordFile().getConfig().getStringList(path);
    }

    public Boolean getBoolean() {
        return instance.getDiscordFile().getConfig().getBoolean(path);
    }
}