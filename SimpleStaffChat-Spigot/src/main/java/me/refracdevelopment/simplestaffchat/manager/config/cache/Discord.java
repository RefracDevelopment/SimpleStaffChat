package me.refracdevelopment.simplestaffchat.manager.config.cache;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;

public class Discord {

    // Webhooks
    public String STAFFCHAT_WEBHOOK;
    public String DEVCHAT_WEBHOOK;
    public String ADMINCHAT_WEBHOOK;
    public String JOIN_WEBHOOK;

    // General
    public boolean DISCORD_EMBED;
    public String DISCORD_TITLE;
    public String DISCORD_FOOTER;

    // Format
    public String DISCORD_FORMAT;
    public String DISCORD_JOIN_FORMAT;
    public String DISCORD_LEAVE_FORMAT;

    public Discord() {
        loadConfig();
    }

    public void loadConfig() {
        // Webhooks
        STAFFCHAT_WEBHOOK = SimpleStaffChat.getInstance().getDiscordFile().getString("webhook.staffchat");
        DEVCHAT_WEBHOOK = SimpleStaffChat.getInstance().getDiscordFile().getString("webhook.devchat");
        ADMINCHAT_WEBHOOK = SimpleStaffChat.getInstance().getDiscordFile().getString("webhook.adminchat");
        JOIN_WEBHOOK = SimpleStaffChat.getInstance().getDiscordFile().getString("webhook.join");

        // General
        DISCORD_EMBED = SimpleStaffChat.getInstance().getDiscordFile().getBoolean("discord.embed-message");
        DISCORD_TITLE = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.title");
        DISCORD_FOOTER = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.footer");

        // Format
        DISCORD_FORMAT = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.format.format");
        DISCORD_JOIN_FORMAT = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.format.join-format");
        DISCORD_LEAVE_FORMAT = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.format.leave-format");
    }
}