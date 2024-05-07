package me.refracdevelopment.simplestaffchat.utilities;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public final class Methods {

    @Getter
    private final List<UUID> staffChatMuted = new ArrayList<>();
    @Getter
    private final List<UUID> adminChatMuted = new ArrayList<>();
    @Getter
    private final List<UUID> devChatMuted = new ArrayList<>();
    @Getter
    private final List<UUID> staffChatPlayers = new ArrayList<>();
    @Getter
    private final List<UUID> adminChatPlayers = new ArrayList<>();
    @Getter
    private final List<UUID> devChatPlayers = new ArrayList<>();


    public void sendStaffChat(CommandSender commandSender, String format, String message) {
        if (commandSender instanceof Player player) {

            RyMessageUtils.broadcast(player, Permissions.STAFFCHAT_SEE, format);

            RyMessageUtils.sendConsole(true, format);

            DiscordImpl.sendStaffChat(player, message
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
            );
        } else {
            RyMessageUtils.broadcast(null, Permissions.STAFFCHAT_SEE, format);

            RyMessageUtils.sendConsole(true, format);

            DiscordImpl.sendDevChat(commandSender, message
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendDevChat(CommandSender commandSender, String format, String message) {
        if (commandSender instanceof Player player) {

            RyMessageUtils.broadcast(player, Permissions.DEVCHAT_SEE, format);

            RyMessageUtils.sendConsole(true, format);

            DiscordImpl.sendDevChat(player, message
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
            );
        } else {
            RyMessageUtils.broadcast(null, Permissions.DEVCHAT_SEE, format);

            RyMessageUtils.sendConsole(true, format);

            DiscordImpl.sendDevChat(commandSender, message
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", "Console")
            );
        }
    }

    public void sendAdminChat(CommandSender commandSender, String format, String message) {
        if (commandSender instanceof Player player) {

            RyMessageUtils.broadcast((Player) commandSender, Permissions.ADMINCHAT_SEE, format);

            RyMessageUtils.sendConsole(true, format);

            DiscordImpl.sendAdminChat(player, message
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
                    .replace("%player%", player.getName())
            );
        } else {
            RyMessageUtils.broadcast(null, Permissions.ADMINCHAT_SEE, format);

            RyMessageUtils.sendConsole(true, format);

            DiscordImpl.sendAdminChat(commandSender, message
                    .replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME)
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
        if (staffChatPlayers.contains(player.getUniqueId()) || devChatPlayers.contains(player.getUniqueId()) || adminChatPlayers.contains(player.getUniqueId())) {
            staffChatPlayers.remove(player.getUniqueId());
            devChatPlayers.remove(player.getUniqueId());
            adminChatPlayers.remove(player.getUniqueId());

            RyMessageUtils.sendPluginMessage(player, "allchat-toggle-on");
        }
    }

    public void hideStaffChat(Player player) {
        if (staffChatMuted.contains(player.getUniqueId())) {
            staffChatMuted.remove(player.getUniqueId());
            RyMessageUtils.sendPluginMessage(player, "staffchat-muted-off");
        } else {
            if (adminChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                adminChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
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
            if (staffChatMuted.contains(player.getUniqueId()) || devChatMuted.contains(player.getUniqueId())) {
                staffChatMuted.remove(player.getUniqueId());
                devChatMuted.remove(player.getUniqueId());
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

    public void hideAll(Player player) {
        if ((staffChatMuted.contains(player.getUniqueId()) && adminChatMuted.contains(player.getUniqueId())) || devChatMuted.contains(player.getUniqueId())) {
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