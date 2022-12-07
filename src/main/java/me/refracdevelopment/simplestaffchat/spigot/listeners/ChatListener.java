package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onStaffChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (ToggleCommand.insc.contains(player.getUniqueId()) && !event.getMessage().startsWith("/")) {
            event.setCancelled(true);

            if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                ToggleCommand.insc.remove(player.getUniqueId());
                locale.sendMessage(player, "toggle-off");
                return;
            }

            String message = event.getMessage();
            String format = Config.MINECRAFT_FORMAT.replace("%message%", message);

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    locale.sendCustomMessage(p, Placeholders.setPlaceholders(player, format));
                }
            });
            Color.log2(Placeholders.setPlaceholders(player, format));
        } else if (event.getMessage().startsWith(Config.STAFFCHAT_SYMBOL) && player.hasPermission(Permissions.STAFFCHAT_SYMBOL)) {
            if (event.getMessage().equalsIgnoreCase(Config.STAFFCHAT_SYMBOL)) return;

            event.setCancelled(true);

            String message = event.getMessage();
            String format = Config.MINECRAFT_FORMAT.replace("%message%", message
                    .replaceFirst(Config.STAFFCHAT_SYMBOL, ""));

            event.getRecipients().forEach(p -> {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    locale.sendCustomMessage(p, Placeholders.setPlaceholders(player, format));
                }
            });
            Color.log2(Placeholders.setPlaceholders(player, format));
        }
    }
}