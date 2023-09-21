package me.refracdevelopment.simplestaffchat.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

public class ChatListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onStaffChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (ToggleCommand.insc.contains(player.getUniqueId())) {
            // requires UnsignedVelocity/VPacketEvents to be installed to work
            // this is purely from my testing may not be required for every setup
            // this cancels the message but kicks the player
            // if signed chat messages are enabled
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                ToggleCommand.insc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_OFF.getString());
                return;
            }

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL.getString()) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL.getString())) return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            // Prevent double messages being sent
            if (ToggleCommand.insc.contains(player.getUniqueId()) || AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                ToggleCommand.insc.remove(player.getUniqueId());
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.STAFFCHAT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.STAFFCHAT_SYMBOL.getString(), ""));

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onAdminChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (AdminToggleCommand.inac.contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF.getString());
                return;
            }

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.ADMINCHAT_SYMBOL.getString()) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.ADMINCHAT_SYMBOL.getString())) return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (ToggleCommand.insc.contains(player.getUniqueId()) || AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                ToggleCommand.insc.remove(player.getUniqueId());
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.ADMINCHAT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.ADMINCHAT_SYMBOL.getString(), ""));

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onDevChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (DevToggleCommand.indc.contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
                DevToggleCommand.indc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.DEVCHAT_TOGGLE_OFF.getString());
                return;
            }

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.DEVCHAT_SYMBOL.getString()) && player.hasPermission(Permissions.DEVCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.DEVCHAT_SYMBOL.getString())) return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (ToggleCommand.insc.contains(player.getUniqueId()) || AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                ToggleCommand.insc.remove(player.getUniqueId());
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }

            String message = event.getMessage();
            String format = Config.DEVCHAT_FORMAT.getString().replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.DEVCHAT_SYMBOL.getString(), ""));

            VelocityStaffChat.getInstance().getServer().getAllPlayers().forEach(p -> {
                if (p.hasPermission(Permissions.DEVCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(player, format));
                }
            });
            Color.log2(Color.translate(player, format));
        }
    }
}