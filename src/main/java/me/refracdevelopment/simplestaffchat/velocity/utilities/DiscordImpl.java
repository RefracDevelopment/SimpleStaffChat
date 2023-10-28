package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

@UtilityClass
public class DiscordImpl {

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!VelocityStaffChat.getInstance().getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(VelocityStaffChat.getInstance().getDiscord().DISCORD_TITLE);
        webhook.setContent(content);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, java.awt.Color color) {
        if (!VelocityStaffChat.getInstance().getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(VelocityStaffChat.getInstance().getDiscord().DISCORD_TITLE)
                .setDescription(content).setColor(color)
                .setFooter(VelocityStaffChat.getInstance().getDiscord().DISCORD_FOOTER, null));

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
        if (VelocityStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        VelocityStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
            } else {
                sendMessage(null, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().STAFFCHAT_WEBHOOK);
            }
        }
    }

    public void sendDevChat(CommandSource commandSource, String content) {
        if (VelocityStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        VelocityStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
            } else {
                sendMessage(null, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().DEVCHAT_WEBHOOK);
            }
        }
    }

    public void sendAdminChat(CommandSource commandSource, String content) {
        if (VelocityStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        VelocityStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
            } else {
                sendMessage(null, VelocityStaffChat.getInstance().getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        VelocityStaffChat.getInstance().getDiscord().ADMINCHAT_WEBHOOK);
            }
        }
    }

    public void sendJoin(JoinType type, Player player, String currentServer, String previousServer) {
        if (VelocityStaffChat.getInstance().getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            VelocityStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "\u00BB"),
                            VelocityStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername()),
                            VelocityStaffChat.getInstance().getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else {
            switch (type) {
                case JOIN:
                    sendMessage(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            VelocityStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                    break;
                case SWITCH:
                    sendMessage(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "\u00BB"),
                            VelocityStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                    break;
                case LEAVE:
                    sendMessage(player, VelocityStaffChat.getInstance().getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            VelocityStaffChat.getInstance().getDiscord().JOIN_WEBHOOK);
                    break;
            }
        }
    }
}