package me.refracdevelopment.simplestaffchat.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

@UtilityClass
public class DiscordImpl {

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!SimpleStaffChat.getInstance().getConfig().DISCORD_ENABLED)
            return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(SimpleStaffChat.getInstance().getDiscord().DISCORD_TITLE);
        webhook.setContent(content);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, java.awt.Color color) {
        if (!SimpleStaffChat.getInstance().getConfig().DISCORD_ENABLED)
            return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(SimpleStaffChat.getInstance().getDiscord().DISCORD_TITLE)
                .setDescription(content).setColor(color)
                .setFooter(SimpleStaffChat.getInstance().getDiscord().DISCORD_FOOTER, null));

        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStaffChat(CommandSource commandSource, String content) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player player) {
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
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
            if (commandSource instanceof Player player) {
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
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

    public void sendDevChat(CommandSource commandSource, String content) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player player) {
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
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
            if (commandSource instanceof Player player) {
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
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

    public void sendAdminChat(CommandSource commandSource, String content) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player player) {
                sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
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
            if (commandSource instanceof Player player) {
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
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

    public void sendJoin(JoinType type, Player player, String currentServer, String previousServer) {
        if (SimpleStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, java.awt.Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "»"),
                            SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, java.awt.Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername()),
                            SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else switch (type) {
            case JOIN:
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                .replace("%server%", currentServer)
                                .replace("%player%", player.getUsername()),
                        SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                break;
            case SWITCH:
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_SWITCH_FORMAT
                                .replace("%from%", previousServer)
                                .replace("%server%", currentServer)
                                .replace("%player%", player.getUsername())
                                .replace("%arrow%", "»"),
                        SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                break;
            case LEAVE:
                sendMessage(player, SimpleStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                .replace("%server%", currentServer)
                                .replace("%player%", player.getUsername()),
                        SimpleStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                break;
        }
    }
}