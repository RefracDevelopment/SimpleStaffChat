package me.refracdevelopment.simplestaffchat.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@UtilityClass
public class Placeholders {

    public String setPlaceholders(CommandSender sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", SimpleStaffChat.getInstance().getLocaleFile().getString("prefix"));
        placeholder = placeholder.replace("%server%", SimpleStaffChat.getInstance().getSettings().SERVER_NAME);
        placeholder = placeholder.replace("%arrow%", "»");
        placeholder = placeholder.replace("%arrow2%", "➥");
        placeholder = placeholder.replace("%arrow_2%", "➥");
        placeholder = placeholder.replace("%star%", "✦");
        placeholder = placeholder.replace("%circle%", "∙");
        placeholder = placeholder.replace("|", "⎟");

        return placeholder;
    }

}