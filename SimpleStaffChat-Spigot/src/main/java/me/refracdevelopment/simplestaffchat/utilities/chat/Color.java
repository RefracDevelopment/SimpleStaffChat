package me.refracdevelopment.simplestaffchat.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@UtilityClass
public class Color {

    public String translate(Player player, String message) {
        message = Placeholders.setPlaceholders(player, message);

        return MiniMessage.miniMessage().serialize(RyMessageUtils.adventureTranslate(player, message));
    }

    public String translate(String message) {
        return MiniMessage.miniMessage().serialize(RyMessageUtils.adventureTranslate(message));
    }

    public void sendMessage(CommandSender sender, String message) {
        sendCustomMessage(sender, SimpleStaffChat.getInstance().getLocaleFile().getString(message));
    }

    public void sendCustomMessage(CommandSender sender, String message) {
        if (message.equalsIgnoreCase("%empty%") || message.contains("%empty%") || message.isEmpty())
            return;

        message = Placeholders.setPlaceholders(sender, message);

        RyMessageUtils.sendSender(sender, "%prefix%" + message);
    }

    public void log(String message) {
        sendCustomMessage(Bukkit.getConsoleSender(), message);
    }
}