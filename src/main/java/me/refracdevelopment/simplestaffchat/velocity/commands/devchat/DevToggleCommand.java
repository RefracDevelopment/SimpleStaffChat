package me.refracdevelopment.simplestaffchat.velocity.commands.devchat;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DevToggleCommand implements SimpleCommand {

    public static List<UUID> indc = new ArrayList<>();

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.DEV_TOGGLE_COMMAND_ENABLED.getBoolean()) return;
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

        if (!player.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION.getString());
            return;
        }

        if (indc.contains(player.getUniqueId())) {
            indc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.DEVCHAT_TOGGLE_OFF.getString());
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            indc.add(player.getUniqueId());
            Color.sendMessage(player, Config.DEVCHAT_TOGGLE_ON.getString());
        }
    }
}