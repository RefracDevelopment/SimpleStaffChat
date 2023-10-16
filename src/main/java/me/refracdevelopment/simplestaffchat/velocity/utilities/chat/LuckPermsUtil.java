package me.refracdevelopment.simplestaffchat.velocity.utilities.chat;

import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.Setter;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Manager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;

@Getter
@Setter
public class LuckPermsUtil extends Manager {

    private LuckPerms luckPerms;

    public LuckPermsUtil(VelocityStaffChat plugin) {
        super(plugin);
    }

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