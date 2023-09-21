package me.refracdevelopment.simplestaffchat.velocity.commands.adminchat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminToggleCommand implements SimpleCommand {

    public static List<UUID> inac = new ArrayList<>();

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.ADMIN_TOGGLE_COMMAND_ENABLED.getBoolean()) return;
        CommandSource commandSource = invocation.source();

        if (commandSource instanceof Player) {
            Player player = (Player) invocation.source();

            if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
                Color.sendMessage(player, Config.NO_PERMISSION.getString());
                return;
            }

            if (inac.contains(player.getUniqueId())) {
                inac.remove(player.getUniqueId());
                Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF.getString());
            } else {
                if (DevToggleCommand.indc.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                    DevToggleCommand.indc.remove(player.getUniqueId());
                    ToggleCommand.insc.remove(player.getUniqueId());
                }
                inac.add(player.getUniqueId());
                Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_ON.getString());
            }
        }
    }
}