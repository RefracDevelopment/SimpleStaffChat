package me.refracdevelopment.simplestaffchat.velocity.commands.devchat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DevToggleCommand implements SimpleCommand {

    public static List<UUID> indc = new ArrayList<>();

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.DEV_TOGGLE_COMMAND_ENABLED.getBoolean()) return;
        CommandSource commandSource = invocation.source();

        if (!(commandSource instanceof Player)) return;

        Player player = (Player) invocation.source();

        Methods.toggleDevChat(player);
    }
}