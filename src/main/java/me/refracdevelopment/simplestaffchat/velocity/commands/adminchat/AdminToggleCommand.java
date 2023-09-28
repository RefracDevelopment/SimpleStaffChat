package me.refracdevelopment.simplestaffchat.velocity.commands.adminchat;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminToggleCommand implements SimpleCommand {

    public static List<UUID> inac = new ArrayList<>();

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.ADMIN_TOGGLE_COMMAND_ENABLED.getBoolean()) return;
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

        Methods.toggleAdminChat(player);
    }
}