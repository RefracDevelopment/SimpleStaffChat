package me.refracdevelopment.simplestaffchat.bungee.commands.devchat;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DevToggleCommand extends Command {
    
    private final BungeeStaffChat plugin;
    
    public DevToggleCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().DEV_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().DEV_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            Color.sendMessage(commandSender, "no-console");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if (!player.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(commandSender, "no-permission");
            return;
        }

        Methods.toggleDevChat(player);
    }
}