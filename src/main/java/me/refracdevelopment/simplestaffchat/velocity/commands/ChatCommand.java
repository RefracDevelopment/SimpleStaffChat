package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

public class ChatCommand implements SimpleCommand {

    private VelocityStaffChat plugin;

    public ChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        if (!plugin.getCommands().CHAT_COMMAND_ENABLED) return;
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        switch (invocation.arguments()[0]) {
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
