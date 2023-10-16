package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;

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

    public Discord(SimpleStaffChat plugin) {
        super(plugin);
    }

    public void loadConfig() {
        // Webhooks
        STAFFCHAT_WEBHOOK = plugin.getDiscordFile().getString("webhook.staffchat");
        DEVCHAT_WEBHOOK = plugin.getDiscordFile().getString("webhook.devchat");
        ADMINCHAT_WEBHOOK = plugin.getDiscordFile().getString("webhook.adminchat");
        JOIN_WEBHOOK = plugin.getDiscordFile().getString("webhook.join");

        // General
        DISCORD_ENABLED = plugin.getDiscordFile().getBoolean("discord.enabled");
        DISCORD_EMBED = plugin.getDiscordFile().getBoolean("discord.embed-message");
        DISCORD_TITLE = plugin.getDiscordFile().getString("discord.title");
        DISCORD_FOOTER = plugin.getDiscordFile().getString("discord.footer");
    }
}