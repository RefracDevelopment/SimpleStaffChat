package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

public class ReloadCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public ReloadCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.RELOAD_COMMAND_ENABLED.getBoolean()) return;

        CommandSource commandSource = invocation.source();

        if (!commandSource.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
            return;
        }

        plugin.loadFiles();
        Color.sendMessage(commandSource, Config.RELOAD.getString());
    }
}