package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import net.kyori.adventure.text.Component;
import org.slf4j.event.Level;

public class Color {

    public static String translate(CommandSource sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        return translate(source.replace("&", "§"));
    }

    public static String translate(String source) {
        return source.replace("&", "§");
    }

    public static void sendMessage(CommandSource sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%") || source.equals("")) return;

        source = Placeholders.setPlaceholders(sender, source);

        sender.sendMessage(Component.text(translate(source.replace("&", "§"))));
    }

    public static void log(Level type, String message) {
        switch (type) {
            case INFO:
                VelocityStaffChat.getInstance().getLogger().info(Config.PREFIX.getString() + message);
                break;
            case WARN:
                VelocityStaffChat.getInstance().getLogger().warn(Config.PREFIX.getString() + " " + message);
                break;
            case ERROR:
                VelocityStaffChat.getInstance().getLogger().error(Config.PREFIX.getString() + " " + message);
                break;
            case DEBUG:
                VelocityStaffChat.getInstance().getLogger().debug(Config.PREFIX.getString() + " " + message);
                break;
        }
    }

    public static void log2(String message) {
        VelocityStaffChat.getInstance().getLogger().info(message);
    }
}