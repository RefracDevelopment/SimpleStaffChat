package me.refracdevelopment.simplestaffchat.spigot.utilities;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

@UtilityClass
public class DiscordImpl {

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!SimpleStaffChat.getInstance().getDiscord().DISCORD_ENABLED)
            return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(SimpleStaffChat.getInstance().getDiscord().DISCORD_TITLE);
        webhook.setContent(ChatColor.stripColor(content));

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, Color color) {
        if (!SimpleStaffChat.getInstance().getDiscord().DISCORD_ENABLED)
            return;

        DiscordWebhook webhook = new DiscordWebhook(url);

        if (player != null) {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setAuthor(ChatColor.stripColor(player.getName()), null, "https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1")
                    .setTitle(SimpleStaffChat.getInstance().getDiscord().DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(SimpleStaffChat.getInstance().getDiscord().DISCORD_FOOTER, null));
        } else {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(SimpleStaffChat.getInstance().getDiscord().DISCORD_TITLE)
                    .setDescription(ChatColor.stripColor(content)).setColor(color)
                    .setFooter(SimpleStaffChat.getInstance().getDiscord().DISCORD_FOOTER, null));
        }

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStaffChat(CommandSender commandSender, String content) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName())
                                .replace("%message%", content)
                        ,
                        SimpleStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSender instanceof com.velocitypowered.api.proxy.Player) {
                Player player = (Player) commandSender;
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName())
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
            } else {
                sendMessage(null, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
            }
        }
    }

    public void sendDevChat(CommandSender commandSender, String content) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName())
                                .replace("%message%", content)
                        ,
                        SimpleStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName())
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
            } else {
                sendMessage(null, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
            }
        }
    }

    public void sendAdminChat(CommandSender commandSender, String content) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName())
                                .replace("%message%", content)
                        ,
                        SimpleStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName())
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
            } else {
                sendMessage(null, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        SimpleStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
            }
        }
    }

    public void sendJoin(JoinType type, Player player) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                    .replace("%player%", player.getName()),
                            SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.GREEN);
                    break;
                case LEAVE:
                    sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                    .replace("%player%", player.getName()),
                            SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else switch (type) {
            case JOIN:
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName()),
                        SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                break;
            case LEAVE:
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                                .replace("%player%", player.getName()),
                        SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                break;
        }
    }
}