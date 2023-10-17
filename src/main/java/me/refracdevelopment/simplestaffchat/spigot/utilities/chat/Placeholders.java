package me.refracdevelopment.simplestaffchat.spigot.utilities.chat;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Placeholders extends Manager {

    public Placeholders(SimpleStaffChat plugin) {
        super(plugin);
    }

    public String setPlaceholders(CommandSender sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", plugin.getSettings().PREFIX);
        placeholder = placeholder.replace("%server%", plugin.getSettings().SERVER_NAME);
        if (sender instanceof Player) {
            Player player = (Player) sender;

            placeholder = placeholder.replace("%player%", player.getName());
            placeholder = placeholder.replace("%displayname%", player.getDisplayName());
        }
        placeholder = placeholder.replace("%arrow%", "\u00BB");
        placeholder = placeholder.replace("%arrow2%", "\u27A5");
        placeholder = placeholder.replace("%arrow_2%", "\u27A5");
        placeholder = placeholder.replace("%star%", "\u2726");
        placeholder = placeholder.replace("%circle%", "\u2219");
        placeholder = placeholder.replace("|", "\u239F");

        return placeholder;
    }
}