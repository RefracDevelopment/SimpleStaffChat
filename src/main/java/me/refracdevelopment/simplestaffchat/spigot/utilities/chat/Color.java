package me.refracdevelopment.simplestaffchat.spigot.utilities.chat;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Color {

    public String translate(CommandSender sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        if (sender instanceof Player && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders((Player) sender, translate(source));
        } else return translate(source);
    }

    public  String translate(String message) {
        return HexUtils.colorify(message);
    }

    public List<String> translate(List<String> source) {
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