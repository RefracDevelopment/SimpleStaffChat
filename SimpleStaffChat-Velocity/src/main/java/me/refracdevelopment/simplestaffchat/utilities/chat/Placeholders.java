package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;

@UtilityClass
public class Placeholders {

    public String setPlaceholders(CommandSource sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", SimpleStaffChat.getInstance().getLocaleFile().getString("prefix"));

        if (sender instanceof Player player) {
            placeholder = placeholder.replace("%player%", player.getUsername());

            if (LuckPermsUtil.getLuckPerms() != null) {
                placeholder = placeholder.replace("%luckperms_prefix%", LuckPermsUtil.getPrefix(player));
                placeholder = placeholder.replace("%luckperms_suffix%", LuckPermsUtil.getSuffix(player));
            } else {
                placeholder = placeholder.replace("%luckperms_prefix%", "N/A");
                placeholder = placeholder.replace("%luckperms_suffix%", "N/A");
            }

            if (player.getCurrentServer().isPresent()) {
                placeholder = placeholder.replace("%server%", player.getCurrentServer().get().getServerInfo().getName());
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