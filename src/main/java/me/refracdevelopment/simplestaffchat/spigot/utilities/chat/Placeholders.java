package me.refracdevelopment.simplestaffchat.spigot.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@UtilityClass
public class Placeholders {

    public String setPlaceholders(CommandSender sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", SimpleStaffChat.getInstance().getLocaleFile().getString("prefix"));
        placeholder = placeholder.replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME);
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