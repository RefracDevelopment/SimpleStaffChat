package me.refracdevelopment.simplestaffchat.velocity.config.cache;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;

public class Discord extends Manager {

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
    public String DISCORD_FORMAT;
    public String DISCORD_JOIN_FORMAT;
    public String DISCORD_SWITCH_FORMAT;
    public String DISCORD_LEAVE_FORMAT;

    public Discord(VelocityStaffChat plugin) {
        super(plugin);
        loadConfig();
    }

    public void loadConfig() {
        STAFFCHAT_WEBHOOK = plugin.getDiscordFile().getString("webhook.staffchat");
        DEVCHAT_WEBHOOK = plugin.getDiscordFile().getString("webhook.devchat");
        ADMINCHAT_WEBHOOK = plugin.getDiscordFile().getString("webhook.adminchat");
        JOIN_WEBHOOK = plugin.getDiscordFile().getString("webhook.join");
        DISCORD_ENABLED = plugin.getDiscordFile().getBoolean("discord.enabled");
        DISCORD_EMBED = plugin.getDiscordFile().getBoolean("discord.embed-message");
        DISCORD_TITLE = plugin.getDiscordFile().getString("discord.title");
        DISCORD_FOOTER = plugin.getDiscordFile().getString("discord.footer");
        DISCORD_FORMAT = plugin.getDiscordFile().getString("discord.velocity.format");
        DISCORD_JOIN_FORMAT = plugin.getDiscordFile().getString("discord.velocity.join-format");
        DISCORD_SWITCH_FORMAT = plugin.getDiscordFile().getString("discord.velocity.switch-format");
        DISCORD_LEAVE_FORMAT = plugin.getDiscordFile().getString("discord.velocity.leave-format");
    }
}