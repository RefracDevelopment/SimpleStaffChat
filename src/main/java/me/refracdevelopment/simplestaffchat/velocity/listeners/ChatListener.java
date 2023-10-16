package me.refracdevelopment.simplestaffchat.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;

public class ChatListener extends Manager {

    public ChatListener(VelocityStaffChat plugin) {
        super(plugin);
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onStaffChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId())) {
            // requires UnsignedVelocity/VPacketEvents to be installed to work
            // this is purely from my testing may not be required for every setup
            // this cancels the message but kicks the player
            // if signed chat messages are enabled
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, plugin.getConfig().STAFFCHAT_TOGGLE_OFF);
                return;
            }

            String message = event.getMessage();
            String format = plugin.getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendStaffChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().STAFFCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().STAFFCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().STAFFCHAT_SYMBOL)) return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            // Prevent double messages being sent
            if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getConfig().STAFFCHAT_SYMBOL, "");
            String format = plugin.getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendStaffChat(player, format, message);
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onAdminChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, plugin.getConfig().ADMINCHAT_TOGGLE_OFF);
                return;
            }

            String message = event.getMessage();
            String format = plugin.getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendAdminChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().ADMINCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().ADMINCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().ADMINCHAT_SYMBOL)) return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getConfig().ADMINCHAT_SYMBOL, "");
            String format = plugin.getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendAdminChat(player, format, message);
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onDevChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
                plugin.getColor().sendMessage(player, plugin.getConfig().DEVCHAT_TOGGLE_OFF);
                return;
            }

            String message = event.getMessage();
            String format = plugin.getConfig().DEVCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendDevChat(player, format, message);
        } else if (event.getMessage().startsWith(plugin.getConfig().DEVCHAT_SYMBOL) && player.hasPermission(plugin.getPermissions().DEVCHAT_SYMBOL) && plugin.getConfig().SYMBOLS) {
            if (event.getMessage().equalsIgnoreCase(plugin.getConfig().DEVCHAT_SYMBOL)) return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (plugin.getMethods().getStaffChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getAdminChatPlayers().contains(player.getUniqueId()) || plugin.getMethods().getDevChatPlayers().contains(player.getUniqueId())) {
                plugin.getMethods().getStaffChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getAdminChatPlayers().remove(player.getUniqueId());
                plugin.getMethods().getDevChatPlayers().remove(player.getUniqueId());
            }

            String message = event.getMessage().replaceFirst(plugin.getConfig().DEVCHAT_SYMBOL, "");
            String format = plugin.getConfig().DEVCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            plugin.getMethods().sendDevChat(player, format, message);
        }
    }
}