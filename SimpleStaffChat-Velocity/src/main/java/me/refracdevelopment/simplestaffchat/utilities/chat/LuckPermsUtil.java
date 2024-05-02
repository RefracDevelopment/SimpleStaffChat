package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

@UtilityClass
public class LuckPermsUtil {

    @Getter
    @Setter
    private static LuckPerms luckPerms;

    public String getPrefix(Player player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getPrefix() == null) return "";
        return lpUser.getCachedData().getMetaData().getPrefix();
    }

    public String getSuffix(Player player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getSuffix() == null) return "";
        return lpUser.getCachedData().getMetaData().getSuffix();
    }
}