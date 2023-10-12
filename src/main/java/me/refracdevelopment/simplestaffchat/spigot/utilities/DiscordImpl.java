package me.refracdevelopment.simplestaffchat.spigot.utilities;

import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Discord;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

public class DiscordImpl {

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!Discord.DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(Discord.DISCORD_TITLE);
        webhook.setContent(ChatColor.stripColor(content));

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, Color color) {
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

    public void sendStaffChat(@Nullable Player player, String content) {
        if (Discord.DISCORD_EMBED)
            sendEmbed(player, content, Discord.STAFFCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(player, content, Discord.STAFFCHAT_WEBHOOK);
    }

    public void sendDevChat(@Nullable Player player, String content) {
        if (Discord.DISCORD_EMBED)
            sendEmbed(player, content, Discord.DEVCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(player, content, Discord.DEVCHAT_WEBHOOK);
    }

    public void sendAdminChat(@Nullable Player player, String content) {
        if (Discord.DISCORD_EMBED)
            sendEmbed(player, content, Discord.ADMINCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(player, content, Discord.ADMINCHAT_WEBHOOK);
    }

    public void sendJoin(Player player, JoinType type, String content) {
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