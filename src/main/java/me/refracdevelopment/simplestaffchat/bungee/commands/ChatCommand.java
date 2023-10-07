package me.refracdevelopment.simplestaffchat.bungee.commands;

import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Methods;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatCommand extends Command {

    public ChatCommand() {
        super(Commands.CHAT_COMMAND, "", Commands.CHAT_ALIAS);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.CHAT_COMMAND_ENABLED) return;
        if (!(commandSender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        switch (strings[0]) {
            case "staff":
                Methods.toggleStaffChat(player);
                break;
            case "admin":
                Methods.toggleAdminChat(player);
                break;
            case "dev":
                Methods.toggleDevChat(player);
                break;
            case "all":
                Methods.toggleAllChat(player);
                break;
        }
    }

}