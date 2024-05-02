package me.refracdevelopment.simplestaffchat.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@UtilityClass
public class Color {

    public Component translate(Player player, String message) {
        message = Placeholders.setPlaceholders(player, message);

        return RyMessageUtils.translate(message);
    }

    public Component translate(String message) {
        return RyMessageUtils.translate(message);
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