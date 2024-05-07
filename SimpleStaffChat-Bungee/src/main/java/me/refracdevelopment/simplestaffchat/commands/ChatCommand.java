package me.refracdevelopment.simplestaffchat.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public ChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().CHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().CHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer player))
            return;

        if (!player.hasPermission(plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(player, "no-permission");
            return;
        }

        if (args.length == 0) {
            RyMessageUtils.sendPlayer(player, "&c/" + getName() + " <staff|admin|dev|all>");
            return;
        }

        switch (args[0]) {
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