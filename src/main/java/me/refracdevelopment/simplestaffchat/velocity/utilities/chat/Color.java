package me.refracdevelopment.simplestaffchat.velocity.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

@UtilityClass
public class Color {

    public Component translate(CommandSource sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        return translate(source);
    }

    public Component translate(String source) {
        return MiniMessage.miniMessage().deserialize(source);
    }

    public void sendMessage(CommandSource sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%") || source.isEmpty()) return;

        sender.sendMessage(translate(sender, source));
    }

    public void log(String message) {
        sendMessage(VelocityStaffChat.getInstance().getServer().getConsoleCommandSource(), VelocityStaffChat.getInstance().getConfig().PREFIX + " " + message);
    }

    public void log2(String message) {
        sendMessage(VelocityStaffChat.getInstance().getServer().getConsoleCommandSource(), message);
    }
}