package me.refracdevelopment.simplestaffchat.bungee.utilities.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.metastacking.MetaStackElement;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.SortedMap;

@UtilityClass
public class LuckPermsUtil {

    @Getter
    @Setter
    private LuckPerms luckPerms;

    public String getPrefix(ProxiedPlayer player) {
        User lpUser = getLuckPerms().getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getPrefix() == null) return "N/A";
        return lpUser.getCachedData().getMetaData().getPrefix();
    }

    public String getPrefixWeight(ProxiedPlayer player, String weight) {
        User lpUser = getLuckPerms().getUserManager().getUser(player.getUniqueId());
        if (lpUser == null) return "N/A";
        Group lpGroup = getLuckPerms().getGroupManager().getGroup(lpUser.getPrimaryGroup());
        if (lpGroup == null) return "N/A";
        SortedMap<Integer, String> prefixes = lpGroup.getCachedData().getMetaData().getPrefixes();

        switch (weight) {
            case "highest":
                return prefixes.getOrDefault(prefixes.firstKey(), "N/A");
            case "lowest":
                return prefixes.getOrDefault(prefixes.lastKey(), "N/A");
            default:
                return prefixes.getOrDefault(Integer.parseInt(weight), "N/A");
        }
    }

    public String getSuffix(ProxiedPlayer player) {
        User lpUser = getLuckPerms().getUserManager().getUser(player.getUniqueId());
        if (lpUser.getCachedData().getMetaData().getSuffix() == null) return "N/A";
        return lpUser.getCachedData().getMetaData().getSuffix();
    }
}