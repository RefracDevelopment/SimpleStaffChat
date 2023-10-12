package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.DiscordWebhook;
import me.refracdevelopment.simplestaffchat.shared.JoinType;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Discord;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.io.IOException;

public class DiscordImpl {

    private void sendMessage(@Nullable Player player, String content, String url) {
        if (!Discord.DISCORD_ENABLED.getBoolean()) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        if (player != null) {
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + player.getUniqueId().toString() + "?overlay=1");
        }
        webhook.setUsername(Discord.DISCORD_TITLE.getString());
        webhook.setContent(content);

        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmbed(@Nullable Player player, String content, String url, java.awt.Color color) {
        if (!Discord.DISCORD_ENABLED.getBoolean()) return;

        DiscordWebhook webhook = new DiscordWebhook(url);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(Discord.DISCORD_TITLE.getString())
                .setDescription(content).setColor(color)
                .setFooter(Discord.DISCORD_FOOTER.getString(), null));

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
        if (Discord.DISCORD_EMBED.getBoolean()) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, Discord.DISCORD_FORMAT.getString()
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        Discord.STAFFCHAT_WEBHOOK.getString(), java.awt.Color.BLACK);
            } else {
                sendEmbed(null, Discord.DISCORD_FORMAT.getString()
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        Discord.STAFFCHAT_WEBHOOK.getString(), java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, Discord.DISCORD_FORMAT.getString()
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        Discord.STAFFCHAT_WEBHOOK.getString());
            } else {
                sendMessage(null, Discord.DISCORD_FORMAT.getString()
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        Discord.STAFFCHAT_WEBHOOK.getString());
            }
        }
    }

    public void sendDevChat(CommandSource commandSource, String content) {
        if (Discord.DISCORD_EMBED.getBoolean()) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, Discord.DISCORD_FORMAT.getString()
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        Discord.DEVCHAT_WEBHOOK.getString(), java.awt.Color.BLACK);
            } else {
                sendEmbed(null, Discord.DISCORD_FORMAT.getString()
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        Discord.DEVCHAT_WEBHOOK.getString(), java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, Discord.DISCORD_FORMAT.getString()
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        Discord.DEVCHAT_WEBHOOK.getString());
            } else {
                sendMessage(null, Discord.DISCORD_FORMAT.getString()
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        Discord.DEVCHAT_WEBHOOK.getString());
            }
        }
    }

    public void sendAdminChat(CommandSource commandSource, String content) {
        if (Discord.DISCORD_EMBED.getBoolean()) {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendEmbed(player, Discord.DISCORD_FORMAT.getString()
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content)
                        ,
                        Discord.ADMINCHAT_WEBHOOK.getString(), java.awt.Color.BLACK);
            } else {
                sendEmbed(null, Discord.DISCORD_FORMAT.getString()
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        Discord.ADMINCHAT_WEBHOOK.getString(), java.awt.Color.BLACK);
            }
        } else {
            if (commandSource instanceof Player) {
                Player player = (Player) commandSource;
                sendMessage(player, Discord.DISCORD_FORMAT.getString()
                                .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                .replace("%player%", player.getUsername())
                                .replace("%message%", content),
                        Discord.ADMINCHAT_WEBHOOK.getString());
            } else {
                sendMessage(null, Discord.DISCORD_FORMAT.getString()
                                .replace("%player%", "Console")
                                .replace("%message%", content),
                        Discord.ADMINCHAT_WEBHOOK.getString());
            }
        }
    }

    public void sendJoin(JoinType type, Player player, String currentServer, String previousServer) {
        if (Discord.DISCORD_EMBED.getBoolean()) {
            switch (type) {
                case JOIN:
                    sendEmbed(player, Discord.DISCORD_JOIN_FORMAT.getString()
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            Discord.JOIN_WEBHOOK.getString(), Color.GREEN);
                    break;
                case SWITCH:
                    sendEmbed(player, Discord.DISCORD_SWITCH_FORMAT.getString()
                                    .replace("%from%", previousServer)
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "\u00BB"),
                            Discord.JOIN_WEBHOOK.getString(), Color.CYAN);
                    break;
                case LEAVE:
                    sendEmbed(player, Discord.DISCORD_LEAVE_FORMAT.getString()
                                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                                    .replace("%player%", player.getUsername()),
                            Discord.JOIN_WEBHOOK.getString(), Color.RED);
                    break;
            }
        } else {
            switch (type) {
                case JOIN:
                    sendMessage(player, Discord.DISCORD_JOIN_FORMAT.getString()
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            Discord.JOIN_WEBHOOK.getString());
                    break;
                case SWITCH:
                    sendMessage(player, Discord.DISCORD_SWITCH_FORMAT.getString()
                                    .replace("%from%", previousServer)
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername())
                                    .replace("%arrow%", "\u00BB"),
                            Discord.JOIN_WEBHOOK.getString());
                    break;
                case LEAVE:
                    sendMessage(player, Discord.DISCORD_LEAVE_FORMAT.getString()
                                    .replace("%server%", currentServer)
                                    .replace("%player%", player.getUsername()),
                            Discord.JOIN_WEBHOOK.getString());
                    break;
            }
        }
    }
}