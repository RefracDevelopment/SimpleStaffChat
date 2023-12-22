package me.refracdevelopment.simplestaffchat.bungee.utilities;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

@UtilityClass
public class DiscordImpl {

    private void sendMessage(@Nullable ProxiedPlayer player, String content, String url) {
        if (!BungeeStaffChat.getInstance().getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(BungeeStaffChat.getInstance().getDiscord().DISCORD_TITLE);
        webhook.setContent(content);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable ProxiedPlayer player, String content, String url, java.awt.Color color) {
        if (!BungeeStaffChat.getInstance().getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);

        if (player != null) {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setAuthor(ChatColor.stripColor(player.getName()), null, "https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1")
                    .setTitle(BungeeStaffChat.getInstance().getDiscord().DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(BungeeStaffChat.getInstance().getDiscord().DISCORD_FOOTER, null));
        } else {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(BungeeStaffChat.getInstance().getDiscord().DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(BungeeStaffChat.getInstance().getDiscord().DISCORD_FOOTER, null));
        }

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStaffChat(@Nullable ProxiedPlayer player, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED)
            sendEmbed(player, content, BungeeStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(null, content, BungeeStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
    }

    public void sendDevChat(@Nullable ProxiedPlayer player, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED)
            sendEmbed(player, content, BungeeStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(null, content, BungeeStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
    }

    public void sendAdminChat(@Nullable ProxiedPlayer player, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED)
            sendEmbed(player, content, BungeeStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(null, content, BungeeStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
    }

    public void sendJoin(ProxiedPlayer player, JoinType type, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, content, BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, content, BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, content, BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else {
            sendMessage(player, content, BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
        }
    }
}