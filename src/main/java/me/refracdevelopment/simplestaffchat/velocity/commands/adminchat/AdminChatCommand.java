package me.refracdevelopment.simplestaffchat.velocity.commands.adminchat;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;

public class AdminChatCommand implements SimpleCommand {

    private VelocityStaffChat plugin;

    public AdminChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!plugin.getCommands().ADMINCHAT_COMMAND_ENABLED) return;

        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (!commandSource.hasPermission(plugin.getCommands().ADMINCHAT_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(commandSource, plugin.getConfig().NO_PERMISSION);
            return;
        }

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? plugin.getConfig().ADMINCHAT_FORMAT.replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_ADMINCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendAdminChat(commandSource, format, message);
        }
    }
}