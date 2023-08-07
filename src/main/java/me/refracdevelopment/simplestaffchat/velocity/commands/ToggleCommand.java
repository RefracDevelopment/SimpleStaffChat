package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToggleCommand implements SimpleCommand {

    public static List<UUID> insc = new ArrayList<>();

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.TOGGLE_COMMAND_ENABLED.getBoolean()) return;
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

        if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION.getString());
            return;
        }

        if (insc.contains(player.getUniqueId())) {
            insc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_OFF.getString());
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || DevToggleCommand.indc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                DevToggleCommand.indc.remove(player.getUniqueId());
            }
            insc.add(player.getUniqueId());
            Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_ON.getString());
        }
    }
}
