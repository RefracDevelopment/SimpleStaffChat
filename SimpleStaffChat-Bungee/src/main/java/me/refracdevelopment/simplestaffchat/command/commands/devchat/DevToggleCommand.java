package me.refracdevelopment.simplestaffchat.command.commands.devchat;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class DevToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevToggleCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().DEV_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().DEV_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer player))
            return;

        if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPlayer(player, "no-permission");
            return;
        }

        Methods.toggleDevChat(player);
    }
}