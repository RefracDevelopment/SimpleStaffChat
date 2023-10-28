package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;
import me.refracdevelopment.simplestaffchat.velocity.utilities.chat.Color;

public class HideCommand implements SimpleCommand {

    private VelocityStaffChat plugin;

    public HideCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        if (!plugin.getCommands().STAFF_HIDE_COMMAND_ENABLED) return;
        if (!(invocation.source() instanceof Player)) return;
        
        Player player = (Player) invocation.source();

        if (!player.hasPermission(plugin.getCommands().STAFF_HIDE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, plugin.getConfig().NO_PERMISSION);
            return;
        }

        Methods.hideStaffChat(player);
    }
}