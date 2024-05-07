package me.refracdevelopment.simplestaffchat.commands.adminchat;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AdminToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public AdminToggleCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().ADMIN_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().ADMIN_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer player))
            return;

        if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPlayer(player, "no-permission");
            return;
        }

        Methods.toggleAdminChat(player);
    }
}