package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.Setter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

public class LuckPermsUtil {

    private LuckPermsUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Getter
    @Setter
    private static LuckPerms luckPerms;

    public static String getPrefix(Player player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getPrefix() == null) return "";
        return lpUser.getCachedData().getMetaData().getPrefix();
    }

    public static String getSuffix(Player player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getSuffix() == null) return "";
        return lpUser.getCachedData().getMetaData().getSuffix();
    }
}