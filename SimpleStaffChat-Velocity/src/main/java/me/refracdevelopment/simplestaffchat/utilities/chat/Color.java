package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

@UtilityClass
public class Color {

    public Component translate(CommandSource sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        return translate(source);
    }

    public Component translate(String message) {
        message = message
                .replaceAll("&1", "<dark_blue>")
                .replaceAll("&2", "<dark_green>")
                .replaceAll("&3", "<dark_aqua>")
                .replaceAll("&4", "<dark_red>")
                .replaceAll("&5", "<dark_purple>")
                .replaceAll("&6", "<gold>")
                .replaceAll("&7", "<gray>")
                .replaceAll("&8", "<dark_gray>")
                .replaceAll("&9", "<blue>")
                .replaceAll("&a", "<green>")
                .replaceAll("&b", "<aqua>")
                .replaceAll("&c", "<red>")
                .replaceAll("&d", "<light_purple>")
                .replaceAll("&e", "<yellow>")
                .replaceAll("&f", "<white>")
                .replaceAll("&l", "<bold>")
                .replaceAll("&k", "<obfuscated>")
                .replaceAll("&m", "<strikethrough>")
                .replaceAll("&n", "<underline>")
                .replaceAll("&o", "<italic>")
                .replaceAll("&r", "<reset>");

        return MiniMessage.miniMessage().deserialize(message);
    }

    public void sendConfigMessage(CommandSource sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%") || source.isEmpty())
            return;

        sender.sendMessage(translate(sender, "%prefix%" + SimpleStaffChat.getInstance().getLocaleFile().getString(source)));
    }

    public void sendMessage(CommandSource sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%") || source.isEmpty())
            return;

        sender.sendMessage(translate(sender, "%prefix%" + source));
    }

    public void log(String message) {
        sendMessage(SimpleStaffChat.getInstance().getServer().getConsoleCommandSource(), message);
    }

    public void log2(String message) {
        sendMessage(SimpleStaffChat.getInstance().getServer().getConsoleCommandSource(), message);
    }
}