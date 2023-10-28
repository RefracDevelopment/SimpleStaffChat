package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;

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
        DISCORD_ENABLED = SimpleStaffChat.getInstance().getDiscordFile().getBoolean("discord.enabled");
        DISCORD_EMBED = SimpleStaffChat.getInstance().getDiscordFile().getBoolean("discord.embed-message");
        DISCORD_TITLE = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.title");
        DISCORD_FOOTER = SimpleStaffChat.getInstance().getDiscordFile().getString("discord.footer");
    }
}