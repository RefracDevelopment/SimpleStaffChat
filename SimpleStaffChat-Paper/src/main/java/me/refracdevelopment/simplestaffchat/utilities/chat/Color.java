package me.refracdevelopment.simplestaffchat.utilities.chat;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Color {

    public Component translate(CommandSender sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        if (sender instanceof Player && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders((Player) sender, ((TextComponent) translate(source)).content()));
        } else
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

    public List<Component> translate(List<String> source) {
        return source.stream().map(Color::translate).collect(Collectors.toList());
    }

    public void sendMessage(CommandSender sender, String message) {
        sendCustomMessage(sender, SimpleStaffChat.getInstance().getLocaleFile().getString(message));
    }

    public void sendCustomMessage(CommandSender sender, String message) {
        if (message.equalsIgnoreCase("%empty%") || message.contains("%empty%") || message.isEmpty()) return;

        sender.sendMessage(translate(sender, message));
    }

    public void log(String message) {
        sendCustomMessage(Bukkit.getConsoleSender(), SimpleStaffChat.getInstance().getLocaleFile().getString("prefix") + " " + message);
    }

    // Used for console StaffChat messages
    public void log2(String message) {
        sendCustomMessage(Bukkit.getConsoleSender(), message);
    }
}