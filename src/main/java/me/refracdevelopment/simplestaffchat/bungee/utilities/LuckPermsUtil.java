package me.refracdevelopment.simplestaffchat.bungee.utilities;

import lombok.Getter;
import lombok.Setter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
@Setter
public class LuckPermsUtil extends Manager {

    private LuckPerms luckPerms;

    public LuckPermsUtil(BungeeStaffChat plugin) {
        super(plugin);
    }

    public String getPrefix(ProxiedPlayer player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getPrefix() == null) return "";
        return lpUser.getCachedData().getMetaData().getPrefix();
    }

    public String getSuffix(ProxiedPlayer player) {
        User lpUser = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getSuffix() == null) return "";
        return lpUser.getCachedData().getMetaData().getSuffix();
    }
}