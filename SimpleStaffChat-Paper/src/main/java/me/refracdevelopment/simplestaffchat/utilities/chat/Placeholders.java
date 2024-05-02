package me.refracdevelopment.simplestaffchat.utilities.chat;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Placeholders {

    private Placeholders() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String setPlaceholders(CommandSender sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", SimpleStaffChat.getInstance().getLocaleFile().getString("prefix"));
        placeholder = placeholder.replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME);

        if (sender instanceof Player player) {

            placeholder = placeholder.replace("%player%", player.getName());
            placeholder = placeholder.replace("%displayname%", player.getName());
        }

        placeholder = placeholder.replace("%arrow%", "»");
        placeholder = placeholder.replace("%arrow2%", "➥");
        placeholder = placeholder.replace("%arrow_2%", "➥");
        placeholder = placeholder.replace("%star%", "✦");
        placeholder = placeholder.replace("%circle%", "∙");
        placeholder = placeholder.replace("|", "⎟");

        return placeholder;
    }
}