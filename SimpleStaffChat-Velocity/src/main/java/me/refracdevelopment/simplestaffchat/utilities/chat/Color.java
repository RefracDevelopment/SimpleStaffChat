package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.Component;

@UtilityClass
public class Color {

    public Component translate(Player player, String message) {
        message = Placeholders.setPlaceholders(player, message);

        return RyMessageUtils.translate(player, message);
    }

    public Component translate(String message) {
        return RyMessageUtils.translate(message);
    }

    public void sendMessage(CommandSource sender, String message) {
        sendCustomMessage(sender, SimpleStaffChat.getInstance().getLocaleFile().getString(message));
    }

    public void sendCustomMessage(CommandSource sender, String message) {
        if (message.equalsIgnoreCase("%empty%") || message.contains("%empty%"))
            return;

        message = Placeholders.setPlaceholders(sender, message);

        RyMessageUtils.sendSender(sender, "%prefix%" + message);
    }

    public void log(String message) {
        sendCustomMessage(SimpleStaffChat.getInstance().getServer().getConsoleCommandSource(), message);
    }
}