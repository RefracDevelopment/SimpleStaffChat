package me.refracdevelopment.simplestaffchat.bungee.utilities;

import lombok.Setter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.ColorTranslator;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@UtilityClass
public class LuckPermsUtil {
    @Setter
    private LuckPerms luckPerms;

    public String getPrefix(ProxiedPlayer player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getPrefix() == null) return "";
        return ColorTranslator.translateColorCodes(lpUser.getCachedData().getMetaData().getPrefix());
    }

    public String getSuffix(ProxiedPlayer player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getSuffix() == null) return "";
        return ColorTranslator.translateColorCodes(lpUser.getCachedData().getMetaData().getSuffix());
    }
}
