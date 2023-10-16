package me.refracdevelopment.simplestaffchat.bungee.utilities.chat;

import lombok.Getter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Manager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

@Getter
public class Color extends Manager {

    private HexUtils hexUtils;

    public Color(BungeeStaffChat plugin) {
        super(plugin);
        this.hexUtils = new HexUtils();
    }

    public String translate(CommandSender sender, String source) {
        source = plugin.getPlaceholders().setPlaceholders(sender, source);

        return translate(source);
    }

    public String translate(String source) {
        return hexUtils.colorify(source);
    }

    public void sendMessage(CommandSender sender, String source) {
        if (source.equalsIgnoreCase("%empty%") || source.contains("%empty%")) return;

        source = plugin.getPlaceholders().setPlaceholders(sender, source);

        hexUtils.sendMessage(sender, source);
    }

    public void log(String message) {
        sendMessage(ProxyServer.getInstance().getConsole(), plugin.getConfig().PREFIX + " " + message);
    }

    public void log2(String message) {
        sendMessage(ProxyServer.getInstance().getConsole(), message);
    }
}