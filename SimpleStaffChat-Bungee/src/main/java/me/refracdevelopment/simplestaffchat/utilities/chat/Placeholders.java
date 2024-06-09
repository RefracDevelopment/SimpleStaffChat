package me.refracdevelopment.simplestaffchat.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@UtilityClass
public class Placeholders {

    public String setPlaceholders(CommandSender sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", SimpleStaffChat.getInstance().getLocaleFile().getString("prefix"));

        if (sender instanceof ProxiedPlayer player) {
            placeholder = placeholder.replace("%player%", player.getName());
            placeholder = placeholder.replace("%displayname%", player.getDisplayName());

            if (LuckPermsUtil.getLuckPerms() != null) {
                placeholder = placeholder.replace("%luckperms_prefix%", LuckPermsUtil.getPrefix(player));
                placeholder = placeholder.replace("%luckperms_suffix%", LuckPermsUtil.getSuffix(player));
            } else {
                placeholder = placeholder.replace("%luckperms_prefix%", "N/A");
                placeholder = placeholder.replace("%luckperms_suffix%", "N/A");
            }

            if (player.getServer() != null) {
                placeholder = placeholder.replace("%server%", player.getServer().getInfo().getName());
            } else {
                placeholder = placeholder.replace("%server%", "");
            }
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