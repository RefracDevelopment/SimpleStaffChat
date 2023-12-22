package me.refracdevelopment.simplestaffchat.bungee.utilities.chat;

import lombok.Getter;
import lombok.Setter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LuckPermsUtil {

    @Getter
    @Setter
    private static LuckPerms luckPerms;

    public static String getPrefix(ProxiedPlayer player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getPrefix() == null) return "";
        return lpUser.getCachedData().getMetaData().getPrefix();
    }

    public static String getSuffix(ProxiedPlayer player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getSuffix() == null) return "";
        return lpUser.getCachedData().getMetaData().getSuffix();
    }
}