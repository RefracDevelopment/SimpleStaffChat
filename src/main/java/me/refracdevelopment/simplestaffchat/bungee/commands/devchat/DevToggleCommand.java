package me.refracdevelopment.simplestaffchat.bungee.commands.devchat;

import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DevToggleCommand extends Command {

    public static List<UUID> indc = new ArrayList<>();

    public DevToggleCommand() {
        super(Commands.DEV_TOGGLE_COMMAND, "", Commands.DEV_TOGGLE_ALIAS);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.DEV_TOGGLE_COMMAND_ENABLED) return;
        if (!(commandSender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        Methods.toggleDevChat(player);
    }
}