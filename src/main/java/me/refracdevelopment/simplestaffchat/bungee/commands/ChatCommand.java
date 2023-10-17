package me.refracdevelopment.simplestaffchat.bungee.commands;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatCommand extends Command {

    private final BungeeStaffChat plugin;
    
    public ChatCommand(BungeeStaffChat plugin) {
        super(plugin.getCommands().CHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().CHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!plugin.getCommands().CHAT_COMMAND_ENABLED) return;
        if (!(commandSender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if (!player.hasPermission(plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        switch (strings[0]) {
            case "staff":
                plugin.getMethods().toggleStaffChat(player);
                break;
            case "admin":
                plugin.getMethods().toggleAdminChat(player);
                break;
            case "dev":
                plugin.getMethods().toggleDevChat(player);
                break;
            case "all":
                plugin.getMethods().toggleAllChat(player);
                break;
        }
    }

}