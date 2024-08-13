package me.refracdevelopment.simplestaffchat.utilities;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class Methods {

    @Getter
    private final Set<UUID> staffChatMuted = new HashSet<>();
    @Getter
    private final Set<UUID> adminChatMuted = new HashSet<>();
    @Getter
    private final Set<UUID> devChatMuted = new HashSet<>();
    @Getter
    private final Set<UUID> staffChatPlayers = new HashSet<>();
    @Getter
    private final Set<UUID> adminChatPlayers = new HashSet<>();
    @Getter
    private final Set<UUID> devChatPlayers = new HashSet<>();

    public void sendStaffChat(CommandSender commandSender, String format, String message) {
        if (commandSender instanceof ProxiedPlayer player) {
            if (player.getServer() == null)
                return;

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getServer().getInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                return;
            }

            if (staffChatMuted.contains(player.getUniqueId())) {
                RyMessageUtils.sendPluginMessage(player, "staffchat-muted");
                return;
            }

            SimpleStaffChat.getInstance().getProxy().getPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.STAFFCHAT_SEE) && !staffChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", player.getServer().getInfo().getName())
                            .replace("%player%", player.getName())
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );

            DiscordImpl.sendStaffChat(player, message
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            SimpleStaffChat.getInstance().getProxy().getPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.STAFFCHAT_SEE) && !staffChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                            .replace("%player%", "Console")
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );

            DiscordImpl.sendStaffChat(commandSender, message
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSender commandSender, String format, String message) {
        if (commandSender instanceof ProxiedPlayer player) {
            if (player.getServer() == null)
                return;

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getServer().getInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                return;
            }

            if (devChatMuted.contains(player.getUniqueId())) {
                RyMessageUtils.sendPluginMessage(player, "devchat-muted");
                return;
            }

            SimpleStaffChat.getInstance().getProxy().getPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.DEVCHAT_SEE) && !devChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", player.getServer().getInfo().getName())
                            .replace("%player%", player.getName())
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );

            DiscordImpl.sendDevChat(player, message
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            SimpleStaffChat.getInstance().getProxy().getPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.DEVCHAT_SEE) && !devChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                            .replace("%player%", "Console")
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );

            DiscordImpl.sendDevChat(commandSender, message
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSender commandSender, String format, String message) {
        if (commandSender instanceof ProxiedPlayer player) {
            if (player.getServer() == null)
                return;

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getServer().getInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                return;
            }

            if (adminChatMuted.contains(player.getUniqueId())) {
                RyMessageUtils.sendPluginMessage(player, "adminchat-muted");
                return;
            }

            SimpleStaffChat.getInstance().getProxy().getPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.ADMINCHAT_SEE) && !adminChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", player.getServer().getInfo().getName())
                            .replace("%player%", player.getName())
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );

            DiscordImpl.sendAdminChat(player, message
                    .replace("%server%", player.getServer().getInfo().getName())
                    .replace("%player%", player.getName())
            );
        } else {
            SimpleStaffChat.getInstance().getProxy().getPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.ADMINCHAT_SEE) && !adminChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                            .replace("%player%", "Console")
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );

            DiscordImpl.sendAdminChat(commandSender, message
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );
        }
    }

    public void toggleStaffChat(ProxiedPlayer player) {
        if (staffChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "staffchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                devChatPlayers.remove(player.getUniqueId());
            }

            staffChatPlayers.add(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "staffchat-toggle-on");
        }
    }

    public void toggleAdminChat(ProxiedPlayer player) {
        if (adminChatPlayers.contains(player.getUniqueId())) {
            adminChatPlayers.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "adminchat-toggle-off");
        } else {
            if (devChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                devChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }

            adminChatPlayers.add(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "adminchat-toggle-on");
        }
    }

    public void toggleDevChat(ProxiedPlayer player) {
        if (devChatPlayers.contains(player.getUniqueId())) {
            devChatPlayers.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "devchat-toggle-off");
        } else {
            if (adminChatPlayers.contains(player.getUniqueId()) || staffChatPlayers.contains(player.getUniqueId())) {
                adminChatPlayers.remove(player.getUniqueId());
                staffChatPlayers.remove(player.getUniqueId());
            }

            devChatPlayers.add(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "devchat-toggle-on");
        }
    }

    public void toggleAllChat(ProxiedPlayer player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());

            RyMessageUtils.sendPluginMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(ProxiedPlayer player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "staffchat-muted-off");
        } else {
            if (devChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                devChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }

            staffChatMuted.add(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "staffchat-muted-on");
        }
    }

    public void hideAdminChat(ProxiedPlayer player) {
        if (adminChatMuted.contains(player.getUniqueId())) {
            adminChatMuted.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "adminchat-muted-off");
        } else {
            if (devChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                devChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }

            adminChatMuted.add(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "adminchat-muted-on");
        }
    }

    public void hideDevChat(ProxiedPlayer player) {
        if (devChatMuted.contains(player.getUniqueId())) {
            devChatMuted.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "devchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || staffChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                staffChatMuted.remove(player.getUniqueId());
            }

            devChatMuted.add(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "devchat-muted-on");
        }
    }

    public void hideAllChat(ProxiedPlayer player) {
        if (staffChatMuted.contains(player.getUniqueId()) || adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            adminChatMuted.remove(player.getUniqueId());
            devChatMuted.remove(player.getUniqueId());

            RyMessageUtils.sendPluginMessage(player, "allchat-muted-off");
        } else {
            staffChatMuted.add(player.getUniqueId());
            adminChatMuted.add(player.getUniqueId());
            devChatMuted.add(player.getUniqueId());

            RyMessageUtils.sendPluginMessage(player, "allchat-muted-on");
        }
    }
}