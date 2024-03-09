package me.refracdevelopment.simplestaffchat.velocity.config.cache;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

public class Discord {

    // Webhooks
    public String STAFFCHAT_WEBHOOK;
    public String DEVCHAT_WEBHOOK;
    public String ADMINCHAT_WEBHOOK;
    public String JOIN_WEBHOOK;

    // General
    public boolean DISCORD_ENABLED;
    public boolean DISCORD_EMBED;
    public String DISCORD_TITLE;
    public String DISCORD_FOOTER;

    // Format
    public String DISCORD_FORMAT;
    public String DISCORD_JOIN_FORMAT;
    public String DISCORD_SWITCH_FORMAT;
    public String DISCORD_LEAVE_FORMAT;

    public Discord() {
        loadConfig();
    }

    public void loadConfig() {
        // Webhooks
        STAFFCHAT_WEBHOOK = VelocityStaffChat.getInstance().getDiscordFile().getString("webhook.staffchat");
        DEVCHAT_WEBHOOK = VelocityStaffChat.getInstance().getDiscordFile().getString("webhook.devchat");
        ADMINCHAT_WEBHOOK = VelocityStaffChat.getInstance().getDiscordFile().getString("webhook.adminchat");
        JOIN_WEBHOOK = VelocityStaffChat.getInstance().getDiscordFile().getString("webhook.join");

        // General
        DISCORD_ENABLED = VelocityStaffChat.getInstance().getDiscordFile().getBoolean("discord.enabled");
        DISCORD_EMBED = VelocityStaffChat.getInstance().getDiscordFile().getBoolean("discord.embed-message");
        DISCORD_TITLE = VelocityStaffChat.getInstance().getDiscordFile().getString("discord.title");
        DISCORD_FOOTER = VelocityStaffChat.getInstance().getDiscordFile().getString("discord.footer");

        // Format
        DISCORD_FORMAT = VelocityStaffChat.getInstance().getDiscordFile().getString("discord.format.format");
        DISCORD_JOIN_FORMAT = VelocityStaffChat.getInstance().getDiscordFile().getString("discord.format.join-format");
        DISCORD_SWITCH_FORMAT = VelocityStaffChat.getInstance().getDiscordFile().getString("discord.format.switch-format");
        DISCORD_LEAVE_FORMAT = VelocityStaffChat.getInstance().getDiscordFile().getString("discord.format.leave-format");
    }
}