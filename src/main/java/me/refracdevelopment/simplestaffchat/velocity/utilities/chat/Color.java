package me.refracdevelopment.simplestaffchat.velocity.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Color extends Manager {

    public Color(VelocityStaffChat plugin) {
        super(plugin);
    }

    public Component translate(CommandSource sender, String source) {
        source = plugin.getPlaceholders().setPlaceholders(sender, source);

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
        sendMessage(plugin.getServer().getConsoleCommandSource(), plugin.getConfig().PREFIX + " " + message);
    }

    public void log2(String message) {
        sendMessage(plugin.getServer().getConsoleCommandSource(), message);
    }
}