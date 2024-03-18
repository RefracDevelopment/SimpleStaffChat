package me.refracdevelopment.simplestaffchat.bungee.utilities.chat;

import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@UtilityClass
public class Placeholders {

    public String setPlaceholders(CommandSender sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", BungeeStaffChat.getInstance().getLocaleFile().getString("prefix"));

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (LuckPermsUtil.getLuckPerms() != null) {
                // TODO: Add weighted prefixes support
                // %luckperms_prefix_weight% - get the highest or lowest weight prefix or the weight of the specified weight number
                placeholder = placeholder.replace("%luckperms_prefix%", LuckPermsUtil.getPrefix(player));
                placeholder = placeholder.replace("%luckperms_suffix%", LuckPermsUtil.getSuffix(player));
            }

            placeholder = placeholder.replace("%player%", player.getName());
            placeholder = placeholder.replace("%displayname%", player.getDisplayName());
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