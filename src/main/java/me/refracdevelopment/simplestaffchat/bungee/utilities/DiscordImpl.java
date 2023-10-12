package me.refracdevelopment.simplestaffchat.bungee.utilities;

import me.refracdevelopment.simplestaffchat.bungee.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

public class DiscordImpl {

    private void sendMessage(@Nullable ProxiedPlayer player, String content, String url) {
        if (!Discord.DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(Discord.DISCORD_TITLE);
        webhook.setContent(content);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable ProxiedPlayer player, String content, String url, java.awt.Color color) {
        if (!Discord.DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);

        if (player != null) {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setAuthor(ChatColor.stripColor(player.getName()), null, "https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1")
                    .setTitle(Discord.DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(Discord.DISCORD_FOOTER, null));
        } else {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(Discord.DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(Discord.DISCORD_FOOTER, null));
        }

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStaffChat(@Nullable ProxiedPlayer player, String content) {
        if (Discord.DISCORD_EMBED)
            sendEmbed(player, content, Discord.STAFFCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(null, content, Discord.STAFFCHAT_WEBHOOK);
    }

    public void sendDevChat(@Nullable ProxiedPlayer player, String content) {
        if (Discord.DISCORD_EMBED)
            sendEmbed(player, content, Discord.DEVCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(null, content, Discord.DEVCHAT_WEBHOOK);
    }

    public void sendAdminChat(@Nullable ProxiedPlayer player, String content) {
        if (Discord.DISCORD_EMBED)
            sendEmbed(player, content, Discord.ADMINCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(null, content, Discord.ADMINCHAT_WEBHOOK);
    }

    public void sendJoin(ProxiedPlayer player, JoinType type, String content) {
        if (Discord.DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, content, Discord.JOIN_WEBHOOK, Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, content, Discord.JOIN_WEBHOOK, Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, content, Discord.JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else {
            sendMessage(player, content, Discord.JOIN_WEBHOOK);
        }
    }
}