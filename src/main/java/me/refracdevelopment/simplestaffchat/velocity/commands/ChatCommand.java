package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class ChatCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public ChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (invocation.arguments().length == 0) {
            Color.sendMessage(player, "&c/" + plugin.getCommands().CHAT_COMMAND_ALIASESES.get(0) + " <staff|admin|dev|all>");
            return;
        }

        switch (invocation.arguments()[0]) {
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
