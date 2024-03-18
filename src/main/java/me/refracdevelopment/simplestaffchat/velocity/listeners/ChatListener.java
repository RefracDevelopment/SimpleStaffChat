package me.refracdevelopment.simplestaffchat.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class ChatListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onStaffChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;
        if (!player.getCurrentServer().isPresent())
            return;

        if (Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
            // requires SignedVelocity to be installed to work
            // this is purely from my testing may not be required for every setup
            // this cancels the message but kicks the player
            // if signed chat messages are enabled
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(VelocityStaffChat.getInstance().getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().STAFFCHAT_TOGGLE_OFF);
                return;
            }

            String message = event.getMessage();
            String format = VelocityStaffChat.getInstance().getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        } else if (event.getMessage().startsWith(VelocityStaffChat.getInstance().getConfig().STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && VelocityStaffChat.getInstance().getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(VelocityStaffChat.getInstance().getConfig().STAFFCHAT_SYMBOL))
                return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            // Prevent double messages being sent
            if (Methods.getStaffChatPlayers().contains(player.getUniqueId()) || Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(VelocityStaffChat.getInstance().getConfig().STAFFCHAT_SYMBOL, "");
            String format = VelocityStaffChat.getInstance().getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onAdminChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;
        if (!player.getCurrentServer().isPresent())
            return;

        if (Methods.getAdminChatPlayers().contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(VelocityStaffChat.getInstance().getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().ADMINCHAT_TOGGLE_OFF);
                return;
            }

            String message = event.getMessage();
            String format = VelocityStaffChat.getInstance().getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        } else if (event.getMessage().startsWith(VelocityStaffChat.getInstance().getConfig().ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && VelocityStaffChat.getInstance().getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(VelocityStaffChat.getInstance().getConfig().ADMINCHAT_SYMBOL))
                return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (Methods.getStaffChatPlayers().contains(player.getUniqueId()) || Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(VelocityStaffChat.getInstance().getConfig().ADMINCHAT_SYMBOL, "");
            String format = VelocityStaffChat.getInstance().getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onDevChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;
        if (!player.getCurrentServer().isPresent())
            return;

        if (Methods.getDevChatPlayers().contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(VelocityStaffChat.getInstance().getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                Color.sendMessage(player, VelocityStaffChat.getInstance().getConfig().DEVCHAT_TOGGLE_OFF);
                return;
            }

            String message = event.getMessage();
            String format = VelocityStaffChat.getInstance().getConfig().DEVCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        } else if (event.getMessage().startsWith(VelocityStaffChat.getInstance().getConfig().DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && VelocityStaffChat.getInstance().getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(VelocityStaffChat.getInstance().getConfig().DEVCHAT_SYMBOL))
                return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (Methods.getStaffChatPlayers().contains(player.getUniqueId()) || Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(VelocityStaffChat.getInstance().getConfig().DEVCHAT_SYMBOL, "");
            String format = VelocityStaffChat.getInstance().getConfig().DEVCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        }
    }
}