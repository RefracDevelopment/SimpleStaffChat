package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class HideCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public HideCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) {
            Color.sendMessage(invocation.source(), "no-console");
            return;
        }
        
        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().HIDE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (invocation.arguments().length == 0) {
            Color.sendMessage(player, "&c/" + plugin.getCommands().HIDE_COMMAND_ALIASESES.get(0) + " <staff|admin|dev|all>");
            return;
        }

        switch (invocation.arguments()[0]) {
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