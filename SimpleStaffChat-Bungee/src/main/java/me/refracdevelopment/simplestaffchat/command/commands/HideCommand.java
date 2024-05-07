package me.refracdevelopment.simplestaffchat.command.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HideCommand extends Command {

    private final SimpleStaffChat plugin;

    public HideCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().HIDE_COMMAND_ALIASES.get(0), "", plugin.getCommands().HIDE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer player))
            return;

        if (!player.hasPermission(plugin.getCommands().HIDE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(commandSender, "no-permission");
            return;
        }

        if (args.length == 0) {
            RyMessageUtils.sendPlayer(player, "&c/" + getName() + " <staff|admin|dev|all>");
            return;
        }

        switch (args[0]) {
            case "staff":
                Methods.hideStaffChat(player);
                break;
            case "admin":
                Methods.hideAdminChat(player);
                break;
            case "dev":
                Methods.hideDevChat(player);
                break;
            case "all":
                Methods.hideAllChat(player);
                break;
        }
    }
}