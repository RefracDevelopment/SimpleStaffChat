package me.refracdevelopment.simplestaffchat.spigot.utilities.chat;

import dev.rosewood.rosegarden.hook.PlaceholderAPIHook;
import dev.rosewood.rosegarden.utils.HexUtils;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Color extends Manager {

    public Color(SimpleStaffChat plugin) {
        super(plugin);
    }

    public String translate(CommandSender sender, String source) {
        source = plugin.getPlaceholders().setPlaceholders(sender, source);

        if (sender instanceof Player && plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPIHook.applyPlaceholders((Player) sender, translate(source));
        } else return translate(source);
    }

    public String translate(String source) {
        return HexUtils.colorify(source);
    }

    public void log(String message) {
        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        String prefix = locale.getLocaleMessage("prefix");

        locale.sendCustomMessage(Bukkit.getConsoleSender(), prefix + message);
    }

    // Used for console StaffChat messages
    public void log2(String message) {
        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        locale.sendCustomMessage(Bukkit.getConsoleSender(), message);
    }
}