package me.refracdevelopment.simplestaffchat.spigot.utilities.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Color extends Manager {

    private final HexUtils hexUtils;
    private final Placeholders placeholders;

    public Color(SimpleStaffChat plugin) {
        super(plugin);
        this.hexUtils = new HexUtils();
        this.placeholders = new Placeholders(plugin);
    }

    public String translate(CommandSender sender, String source) {
        source = placeholders.setPlaceholders(sender, source);

        if (sender instanceof Player && plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders((Player) sender, translate(source));
        } else return translate(source);
    }

    public String translate(String source) {
        return hexUtils.colorify(source);
    }

    public void sendMessage(CommandSender sender, String source) {
        if (plugin.getConfigFile().getString(source).contains("%empty%") ||
                plugin.getConfigFile().getString(source).equalsIgnoreCase("%empty%") ||
                plugin.getConfigFile().getString(source).isEmpty()) return;

        sender.sendMessage(translate(sender, plugin.getConfigFile().getString(source)));
    }

    public void sendCustomMessage(CommandSender sender, String source) {
        if (source.contains("%empty%") || source.equalsIgnoreCase("%empty%") || source.isEmpty()) return;

        sender.sendMessage(translate(sender, source));
    }

    public void log(String message) {
        sendCustomMessage(plugin.getServer().getConsoleSender(), plugin.getSettings().PREFIX + " " + message);
    }

    // Used for console StaffChat messages
    public void log2(String message) {
        sendCustomMessage(plugin.getServer().getConsoleSender(), message);
    }
}