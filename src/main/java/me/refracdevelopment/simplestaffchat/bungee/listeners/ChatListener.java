package me.refracdevelopment.simplestaffchat.bungee.listeners;

import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.config.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler
    public void onStaffChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (ToggleCommand.insc.contains(player.getUniqueId()) && !event.isCommand()) {
            event.setCancelled(true);

            if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                ToggleCommand.insc.remove(player.getUniqueId());
                Color.sendMessage(player, Config.MESSAGES_TOGGLE_OFF.toString(), true);
                return;
            }

            String message = event.getMessage();
            String format = Config.FORMAT_STAFFCHAT.toString().replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            }
            ProxyServer.getInstance().getConsole().sendMessage(Color.translate(player, format));
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL.toString()) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL.toString())) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.FORMAT_STAFFCHAT.toString().replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message.replaceFirst(Config.STAFFCHAT_SYMBOL.toString(), ""));

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            }
            ProxyServer.getInstance().getConsole().sendMessage(Color.translate(player, format));
        }
    }
}