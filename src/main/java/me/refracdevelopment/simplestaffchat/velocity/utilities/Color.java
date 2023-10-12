package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Color {

    public static String translate(CommandSource sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        return translate(source);
    }

    public static String translate(String source) {
        return source.replace("&", "§");
    }

    public static void sendMessage(CommandSource sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%") || source.isEmpty()) return;

        source = Placeholders.setPlaceholders(sender, source);

        sender.sendMessage(MiniMessage.miniMessage().deserialize(source));
    }

    public static void log(String message) {
        sendMessage(VelocityStaffChat.getInstance().getServer().getConsoleCommandSource(), Config.PREFIX.getString() + " " + message);
    }

    public static void log2(String message) {
        sendMessage(VelocityStaffChat.getInstance().getServer().getConsoleCommandSource(), message);
    }
}