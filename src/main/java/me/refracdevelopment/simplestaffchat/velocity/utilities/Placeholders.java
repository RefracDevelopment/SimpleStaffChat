package me.refracdevelopment.simplestaffchat.velocity.utilities;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;

public class Placeholders {

    public static String setPlaceholders(CommandSource sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", Config.PREFIX.getString());
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (Config.LUCKPERMS.getBoolean() || LuckPermsUtil.getLuckPerms() != null) {
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
            placeholder = placeholder.replace("%player%", player.getUsername());
            placeholder = placeholder.replace("%displayname%", player.getUsername());
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