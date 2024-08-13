package me.refracdevelopment.simplestaffchat.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;

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

    public void sendStaffChat(CommandSource commandSource, String format, String message) {
        if (commandSource instanceof Player player) {
            if (!player.getCurrentServer().isPresent())
                return;

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                return;
            }

            if (staffChatMuted.contains(player.getUniqueId())) {
                RyMessageUtils.sendPluginMessage(player, "staffchat-muted");
                return;
            }

            SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.STAFFCHAT_SEE) && !staffChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                            .replace("%player%", player.getUsername())
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername()));

            DiscordImpl.sendStaffChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.STAFFCHAT_SEE) && !staffChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                            .replace("%player%", "Console")
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console"));

            DiscordImpl.sendStaffChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSource commandSource, String format, String message) {
        if (commandSource instanceof Player player) {
            if (!player.getCurrentServer().isPresent())
                return;

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                return;
            }

            if (devChatMuted.contains(player.getUniqueId())) {
                RyMessageUtils.sendPluginMessage(player, "devchat-muted");
                return;
            }

            SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.DEVCHAT_SEE) && !devChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                            .replace("%player%", player.getUsername())
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername()));

            DiscordImpl.sendDevChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.DEVCHAT_SEE) && !devChatPlayers.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                            .replace("%player%", "Console")
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console"));

            DiscordImpl.sendDevChat(commandSource, message
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSource commandSource, String format, String message) {
        if (commandSource instanceof Player player) {
            if (!player.getCurrentServer().isPresent())
                return;

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                return;
            }

            if (adminChatMuted.contains(player.getUniqueId())) {
                RyMessageUtils.sendPluginMessage(player, "adminchat-muted");
                return;
            }

            SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.ADMINCHAT_SEE) && !adminChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                            .replace("%player%", player.getUsername())
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername()));

            DiscordImpl.sendAdminChat(player, message
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", player.getUsername())
            );
        } else {
            SimpleStaffChat.getInstance().getServer().getAllPlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.hasPermission(Permissions.ADMINCHAT_SEE) && !adminChatMuted.contains(onlinePlayer.getUniqueId())) {
                    RyMessageUtils.sendPlayer(onlinePlayer, format
                            .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                            .replace("%player%", "Console")
                    );
                }
            });

            RyMessageUtils.sendConsole(true, format
                    .replace("%server%", SimpleStaffChat.getInstance().getConfig().SERVER_NAME)
                    .replace("%player%", "Console"));

            DiscordImpl.sendAdminChat(commandSource, message
                    .replace("%player%", "Console")
            );
        }
    }

    public void toggleStaffChat(Player player) {
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

    public void toggleAdminChat(Player player) {
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

    public void toggleDevChat(Player player) {
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

    public void toggleAllChat(Player player) {
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId())
                || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());
            staffChatPlayers.remove(player.getUniqueId());

            RyMessageUtils.sendPluginMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(Player player) {
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

    public void hideAdminChat(Player player) {
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

    public void hideDevChat(Player player) {
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

    public void hideAllChat(Player player) {
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