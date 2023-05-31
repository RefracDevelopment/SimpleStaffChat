package me.refracdevelopment.simplestaffchat.bungee.commands.adminchat;

import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminToggleCommand extends Command {

    public static List<UUID> inac = new ArrayList<>();

    public AdminToggleCommand() {
        super(Commands.ADMIN_TOGGLE_COMMAND, "", Commands.ADMIN_TOGGLE_ALIAS);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.ADMIN_TOGGLE_COMMAND_ENABLED) return;
        if (!(commandSender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION, true);
            return;
        }

        if (inac.contains(player.getUniqueId())) {
            inac.remove(player.getUniqueId());
            Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF, true);
        } else {
            inac.add(player.getUniqueId());
            Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_ON, true);
        }
    }
}