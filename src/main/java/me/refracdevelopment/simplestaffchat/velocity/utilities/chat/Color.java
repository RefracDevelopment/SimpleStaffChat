package me.refracdevelopment.simplestaffchat.velocity.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Color extends Manager {

    public Color(VelocityStaffChat plugin) {
        super(plugin);
    }

    public String translate(CommandSource sender, String source) {
        source = plugin.getPlaceholders().setPlaceholders(sender, source);

        return MiniMessage.miniMessage().deserialize(source).toString();
    }

    public void sendMessage(CommandSource sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%") || source.isEmpty()) return;

        source = plugin.getPlaceholders().setPlaceholders(sender, source);

        sender.sendMessage(MiniMessage.miniMessage().deserialize(source));
    }

    public void log(String message) {
        sendMessage(plugin.getServer().getConsoleCommandSource(), plugin.getConfig().PREFIX + " " + message);
    }

    public void log2(String message) {
        sendMessage(plugin.getServer().getConsoleCommandSource(), message);
    }
}