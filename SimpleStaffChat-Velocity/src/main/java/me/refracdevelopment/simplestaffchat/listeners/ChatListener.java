package me.refracdevelopment.simplestaffchat.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;

public class ChatListener {

    @Subscribe
    public void onStaffChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;
        if (!player.getCurrentServer().isPresent())
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;

        if (Methods.getStaffChatPlayers().contains(player.getUniqueId())) {
            // requires SignedVelocity to be installed on the proxy/backend servers
            // for cancelling chat messages
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                event.setResult(PlayerChatEvent.ChatResult.denied());
                return;
            }

            if (!player.hasPermission(SimpleStaffChat.getInstance().getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                RyMessageUtils.sendPluginMessage(player, "staffchat-toggle-off");
                return;
            }

            String message = event.getMessage();
            String format = SimpleStaffChat.getInstance().getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        } else if (event.getMessage().startsWith(SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-symbol")) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL) && SimpleStaffChat.getInstance().getConfig().SYMBOLS_ENABLED) {
            if (event.getMessage().equalsIgnoreCase(SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-symbol")))
                return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            // Prevent double messages being sent
            if (Methods.getStaffChatPlayers().contains(player.getUniqueId()) || Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                event.setResult(PlayerChatEvent.ChatResult.denied());
                return;
            }

            String message = event.getMessage().replaceFirst(SimpleStaffChat.getInstance().getLocaleFile().getString("staffchat-symbol"), "");
            String format = SimpleStaffChat.getInstance().getConfig().STAFFCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(player, format, message);
        }
    }

    @Subscribe
    public void onAdminChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;
        if (!player.getCurrentServer().isPresent())
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;

        if (Methods.getAdminChatPlayers().contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(SimpleStaffChat.getInstance().getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                RyMessageUtils.sendPluginMessage(player, "adminchat-toggle-off");
                return;
            }

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                event.setResult(PlayerChatEvent.ChatResult.denied());
                return;
            }

            String message = event.getMessage();
            String format = SimpleStaffChat.getInstance().getLocaleFile().getString("adminchat-format")
                    .replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        } else if (event.getMessage().startsWith(SimpleStaffChat.getInstance().getConfig().ADMINCHAT_SYMBOL) && player.hasPermission(Permissions.ADMINCHAT_SYMBOL) && SimpleStaffChat.getInstance().getConfig().SYMBOLS_ENABLED) {
            if (event.getMessage().equalsIgnoreCase(SimpleStaffChat.getInstance().getConfig().ADMINCHAT_SYMBOL))
                return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (Methods.getStaffChatPlayers().contains(player.getUniqueId()) || Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                event.setResult(PlayerChatEvent.ChatResult.denied());
                return;
            }

            String message = event.getMessage().replaceFirst(SimpleStaffChat.getInstance().getConfig().ADMINCHAT_SYMBOL, "");
            String format = SimpleStaffChat.getInstance().getConfig().ADMINCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendAdminChat(player, format, message);
        }
    }

    @Subscribe
    public void onDevChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().startsWith("/"))
            return;
        if (!player.getCurrentServer().isPresent())
            return;
        if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName()))
            return;

        if (Methods.getDevChatPlayers().contains(player.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (!player.hasPermission(SimpleStaffChat.getInstance().getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
                Methods.getDevChatPlayers().remove(player.getUniqueId());
                RyMessageUtils.sendPluginMessage(player, "devchat-toggle-off");
                return;
            }

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                event.setResult(PlayerChatEvent.ChatResult.denied());
                return;
            }

            String message = event.getMessage();
            String format = SimpleStaffChat.getInstance().getConfig().DEVCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        } else if (event.getMessage().startsWith(SimpleStaffChat.getInstance().getConfig().DEVCHAT_SYMBOL) && player.hasPermission(Permissions.DEVCHAT_SYMBOL) && SimpleStaffChat.getInstance().getConfig().SYMBOLS_ENABLED) {
            if (event.getMessage().equalsIgnoreCase(SimpleStaffChat.getInstance().getConfig().DEVCHAT_SYMBOL))
                return;

            event.setResult(PlayerChatEvent.ChatResult.denied());

            if (Methods.getStaffChatPlayers().contains(player.getUniqueId()) || Methods.getAdminChatPlayers().contains(player.getUniqueId()) || Methods.getDevChatPlayers().contains(player.getUniqueId())) {
                Methods.getStaffChatPlayers().remove(player.getUniqueId());
                Methods.getAdminChatPlayers().remove(player.getUniqueId());
                Methods.getDevChatPlayers().remove(player.getUniqueId());
            }

            if (SimpleStaffChat.getInstance().getServers().BLACKLIST_SERVERS.contains(player.getCurrentServer().get().getServerInfo().getName())) {
                RyMessageUtils.sendPluginMessage(player, "blacklisted-server");
                event.setResult(PlayerChatEvent.ChatResult.denied());
                return;
            }

            String message = event.getMessage().replaceFirst(SimpleStaffChat.getInstance().getConfig().DEVCHAT_SYMBOL, "");
            String format = SimpleStaffChat.getInstance().getConfig().DEVCHAT_FORMAT.replace("%server%", player.getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message);

            Methods.sendDevChat(player, format, message);
        }
    }
}