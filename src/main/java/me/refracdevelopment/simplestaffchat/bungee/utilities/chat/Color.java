package me.refracdevelopment.simplestaffchat.bungee.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Color {

    public String translate(CommandSender sender, String source) {
        source = Placeholders.setPlaceholders(sender, source);

        return translate(source);
    }

    public String translate(String source) {
        return HexUtils.colorify(source);
    }

    public List<String> translate(List<String> source) {
        return source.stream().map(Color::translate).collect(Collectors.toList());
    }

    public void sendMessage(CommandSender sender, String message, String... replacements) {
        if (Arrays.stream(replacements).findAny().isPresent())
            sendCustomMessage(sender, BungeeStaffChat.getInstance().getLocaleFile().getString(message).replace("%cmd%", Arrays.stream(replacements).findAny().get()));
        else
            sendCustomMessage(sender, BungeeStaffChat.getInstance().getLocaleFile().getString(message));
    }

    public void sendCustomMessage(CommandSender sender, String message) {
        if (message.equalsIgnoreCase("%empty%") || message.contains("%empty%")) return;

        sender.sendMessage(TextComponent.fromLegacyText(translate(sender, message)));
    }

    public void log(String message) {
        sendCustomMessage(BungeeStaffChat.getInstance().getProxy().getConsole(), BungeeStaffChat.getInstance().getLocaleFile().getString("prefix") + " " + message);
    }

    public void log2(String message) {
        sendCustomMessage(BungeeStaffChat.getInstance().getProxy().getConsole(), message);
    }
}