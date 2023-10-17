package me.refracdevelopment.simplestaffchat.bungee.utilities.chat;

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Manager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

@Getter
public class Color extends Manager {

    private final HexUtils hexUtils;
    private final Placeholders placeholders;
    
    public Color(BungeeStaffChat plugin) {
        super(plugin);
        this.hexUtils = new HexUtils();
        this.placeholders = new Placeholders(plugin);
    }

    public String translate(CommandSender sender, String source) {
        source = placeholders.setPlaceholders(sender, source);

        return translate(source);
    }

    public String translate(String source) {
        return hexUtils.colorify(source);
    }

    public void sendMessage(CommandSender sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;

        sender.sendMessage(TextComponent.fromLegacyText(translate(sender, source)));
    }

    public void log(String message) {
        sendMessage(plugin.getProxy().getConsole(), plugin.getConfig().PREFIX + " " + message);
    }

    public void log2(String message) {
        sendMessage(plugin.getProxy().getConsole(), message);
    }
}