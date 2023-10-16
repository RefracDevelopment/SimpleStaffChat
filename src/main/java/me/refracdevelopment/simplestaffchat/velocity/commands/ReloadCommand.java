package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

public class ReloadCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public ReloadCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        if (!commandSource.hasPermission(plugin.getPermissions().STAFFCHAT_RELOAD)) {
            plugin.getColor().sendMessage(commandSource, plugin.getConfig().NO_PERMISSION);
            return;
        }

        plugin.reloadFiles();
        plugin.getColor().sendMessage(commandSource, plugin.getConfig().RELOAD);
    }
}