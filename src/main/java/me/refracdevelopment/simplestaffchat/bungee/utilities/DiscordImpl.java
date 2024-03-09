package me.refracdevelopment.simplestaffchat.bungee.utilities;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
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

    public void sendStaffChat(CommandSender commandSender, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) commandSender;
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%player%", player.getName())
                                .replace("%message%", content)
                        ,
                        BungeeStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) commandSender;
                sendMessage(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%player%", player.getName())
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
            } else {
                sendMessage(null, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
            }
        }
    }

    public void sendDevChat(CommandSender commandSender, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) commandSender;
                sendEmbed(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%player%", player.getName())
                                .replace("%message%", content)
                        ,
                        BungeeStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) commandSender;
                sendMessage(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%player%", player.getName())
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
            } else {
                sendMessage(null, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
            }
        }
    }

    public void sendAdminChat(CommandSender commandSender, String content) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) commandSender;
                sendEmbed(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%player%", player.getName())
                                .replace("%message%", content)
                        ,
                        BungeeStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) commandSender;
                sendMessage(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%player%", player.getName())
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
            } else {
                sendMessage(null, BungeeStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        BungeeStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
            }
        }
    }

    public void sendJoin(JoinType type, ProxiedPlayer player, String currentServer, String previousServer) {
        if (BungeeStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getName()),
                            BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getName())
                                    .replace("%arrow%", "»"),
                            BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getName()),
                            BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else {
            switch (type) {
                case JOIN:
                    sendMessage(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getName()),
                            BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                    break;
                case SWITCH:
                    sendMessage(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getName())
                                    .replace("%arrow%", "\u00BB"),
                            BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                    break;
                case LEAVE:
                    sendMessage(player, BungeeStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getName()),
                            BungeeStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                    break;
            }
        }
    }
}