package me.refracdevelopment.simplestaffchat.spigot.utilities;

import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

public class DiscordImpl extends Manager {

    public DiscordImpl(SimpleStaffChat plugin) {
        super(plugin);
    }

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!plugin.getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(plugin.getDiscord().DISCORD_TITLE);
        webhook.setContent(ChatColor.stripColor(content));

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, Color color) {
        if (!plugin.getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);

        if (player != null) {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setAuthor(ChatColor.stripColor(player.getName()), null, "https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1")
                    .setTitle(plugin.getDiscord().DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(plugin.getDiscord().DISCORD_FOOTER, null));
        } else {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(plugin.getDiscord().DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(plugin.getDiscord().DISCORD_FOOTER, null));
        }

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStaffChat(@Nullable Player player, String content) {
        if (plugin.getDiscord().DISCORD_EMBED)
            sendEmbed(player, content, plugin.getDiscord().STAFFCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(player, content, plugin.getDiscord().STAFFCHAT_WEBHOOK);
    }

    public void sendDevChat(@Nullable Player player, String content) {
        if (plugin.getDiscord().DISCORD_EMBED)
            sendEmbed(player, content, plugin.getDiscord().DEVCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(player, content, plugin.getDiscord().DEVCHAT_WEBHOOK);
    }

    public void sendAdminChat(@Nullable Player player, String content) {
        if (plugin.getDiscord().DISCORD_EMBED)
            sendEmbed(player, content, plugin.getDiscord().ADMINCHAT_WEBHOOK, Color.BLACK);
        else
            sendMessage(player, content, plugin.getDiscord().ADMINCHAT_WEBHOOK);
    }

    public void sendJoin(Player player, JoinType type, String content) {
        if (plugin.getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, content, plugin.getDiscord().JOIN_WEBHOOK, Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, content, plugin.getDiscord().JOIN_WEBHOOK, Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, content, plugin.getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else {
            sendMessage(player, content, plugin.getDiscord().JOIN_WEBHOOK);
        }
    }
}