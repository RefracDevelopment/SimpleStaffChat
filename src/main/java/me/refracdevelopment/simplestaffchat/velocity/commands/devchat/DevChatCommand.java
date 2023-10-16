package me.refracdevelopment.simplestaffchat.velocity.commands.devchat;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

public class DevChatCommand implements SimpleCommand {

    private VelocityStaffChat plugin;

    public DevChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!plugin.getCommands().DEVCHAT_COMMAND_ENABLED) return;

        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (!commandSource.hasPermission(plugin.getCommands().DEVCHAT_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(commandSource, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? plugin.getConfig().DEVCHAT_FORMAT.replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_DEVCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendDevChat(commandSource, format, message);
        }
    }
}