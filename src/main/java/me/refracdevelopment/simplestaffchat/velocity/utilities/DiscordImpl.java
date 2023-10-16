package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;

public class DiscordImpl extends Manager {

    public DiscordImpl(VelocityStaffChat plugin) {
        super(plugin);
    }

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!plugin.getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(plugin.getDiscord().DISCORD_TITLE);
        webhook.setContent(content);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, java.awt.Color color) {
        if (!plugin.getDiscord().DISCORD_ENABLED) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(plugin.getDiscord().DISCORD_TITLE)
                .setDescription(content).setColor(color)
                .setFooter(plugin.getDiscord().DISCORD_FOOTER, null));

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
        if (plugin.getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        plugin.getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        plugin.getDiscord().STAFFCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        plugin.getDiscord().STAFFCHAT_WEBHOOK);
            } else {
                sendMessage(null, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        plugin.getDiscord().STAFFCHAT_WEBHOOK);
            }
        }
    }

    public void sendDevChat(CommandSource commandSource, String content) {
        if (plugin.getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        plugin.getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        plugin.getDiscord().DEVCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        plugin.getDiscord().DEVCHAT_WEBHOOK);
            } else {
                sendMessage(null, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        plugin.getDiscord().DEVCHAT_WEBHOOK);
            }
        }
    }

    public void sendAdminChat(CommandSource commandSource, String content) {
        if (plugin.getDiscord().DISCORD_EMBED) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        plugin.getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            } else {
                sendEmbed(null, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        plugin.getDiscord().ADMINCHAT_WEBHOOK, java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        plugin.getDiscord().ADMINCHAT_WEBHOOK);
            } else {
                sendMessage(null, plugin.getDiscord().DISCORD_FORMAT
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        plugin.getDiscord().ADMINCHAT_WEBHOOK);
            }
        }
    }

    public void sendJoin(JoinType type, Player player, String currentServer, String previousServer) {
        if (plugin.getDiscord().DISCORD_EMBED) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, plugin.getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            plugin.getDiscord().JOIN_WEBHOOK, Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, plugin.getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "\u00BB"),
                            plugin.getDiscord().JOIN_WEBHOOK, Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, plugin.getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername()),
                            plugin.getDiscord().JOIN_WEBHOOK, Color.RED);
                    break;
            }
        } else {
            switch (type) {
                case JOIN:
                    sendMessage(player, plugin.getDiscord().DISCORD_JOIN_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            plugin.getDiscord().JOIN_WEBHOOK);
                    break;
                case SWITCH:
                    sendMessage(player, plugin.getDiscord().DISCORD_SWITCH_FORMAT
                                    .replace("%from%", previousServer)
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "\u00BB"),
                            plugin.getDiscord().JOIN_WEBHOOK);
                    break;
                case LEAVE:
                    sendMessage(player, plugin.getDiscord().DISCORD_LEAVE_FORMAT
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            plugin.getDiscord().JOIN_WEBHOOK);
                    break;
            }
        }
    }
}