package me.refracdevelopment.simplestaffchat.velocity.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;

public class Placeholders extends Manager {

    public Placeholders(VelocityStaffChat plugin) {
        super(plugin);
    }

    public String setPlaceholders(CommandSource sender, String placeholder) {
        placeholder = placeholder.replace("%prefix%", plugin.getConfig().PREFIX);
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (plugin.getConfig().LUCKPERMS || plugin.getLuckPermsUtil() != null) {
                placeholder = placeholder.replace("%luckperms_prefix%", plugin.getLuckPermsUtil().getPrefix(player));
                placeholder = placeholder.replace("%luckperms_suffix%", plugin.getLuckPermsUtil().getSuffix(player));
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