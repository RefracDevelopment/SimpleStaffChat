package me.refracdevelopment.simplestaffchat.bungee.config.cache;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;

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
        STAFFCHAT_WEBHOOK = BungeeStaffChat.getInstance().getDiscordFile().getString("webhook.staffchat");
        DEVCHAT_WEBHOOK = BungeeStaffChat.getInstance().getDiscordFile().getString("webhook.devchat");
        ADMINCHAT_WEBHOOK = BungeeStaffChat.getInstance().getDiscordFile().getString("webhook.adminchat");
        JOIN_WEBHOOK = BungeeStaffChat.getInstance().getDiscordFile().getString("webhook.join");

        // General
        DISCORD_ENABLED = BungeeStaffChat.getInstance().getDiscordFile().getBoolean("discord.enabled");
        DISCORD_EMBED = BungeeStaffChat.getInstance().getDiscordFile().getBoolean("discord.embed-message");
        DISCORD_TITLE = BungeeStaffChat.getInstance().getDiscordFile().getString("discord.title");
        DISCORD_FOOTER = BungeeStaffChat.getInstance().getDiscordFile().getString("discord.footer");

        // Format
        DISCORD_FORMAT = BungeeStaffChat.getInstance().getDiscordFile().getString("discord.format.format");
        DISCORD_JOIN_FORMAT = BungeeStaffChat.getInstance().getDiscordFile().getString("discord.format.join-format");
        DISCORD_SWITCH_FORMAT = BungeeStaffChat.getInstance().getDiscordFile().getString("discord.format.switch-format");
        DISCORD_LEAVE_FORMAT = BungeeStaffChat.getInstance().getDiscordFile().getString("discord.format.leave-format");
    }
}