package me.refracdevelopment.simplestaffchat.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


public class ToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public ToggleCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().STAFF_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().STAFF_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer player))
            return;

        if (!player.hasPermission(plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(commandSender, "no-permission");
            return;
        }

        Methods.toggleStaffChat(player);
    }
}