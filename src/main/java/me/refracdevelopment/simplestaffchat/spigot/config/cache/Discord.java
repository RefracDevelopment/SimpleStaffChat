package me.refracdevelopment.simplestaffchat.spigot.config.cache;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;

public class Discord {

    // Webhooks
    public static String STAFFCHAT_WEBHOOK;
    public static String DEVCHAT_WEBHOOK;
    public static String ADMINCHAT_WEBHOOK;
    public static String JOIN_WEBHOOK;

    // General
    public static boolean DISCORD_ENABLED;
    public static boolean DISCORD_EMBED;
    public static String DISCORD_TITLE;
    public static String DISCORD_FOOTER;

    public static void loadConfig() {
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