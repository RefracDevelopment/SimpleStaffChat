package me.refracdevelopment.simplestaffchat.commands.adminchat;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;

public class AdminChatCommand implements SimpleCommand {

    private final SimpleStaffChat plugin;

    public AdminChatCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (!commandSource.hasPermission(plugin.getCommands().ADMINCHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(commandSource, "no-permission");
            return;
        }

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? plugin.getConfig().ADMINCHAT_FORMAT
                    .replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", ((Player) commandSource).getUsername())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_ADMINCHAT_FORMAT
                    .replace("%server%", "N/A")
                    .replace("%player%", "Console")
                    .replace("%message%", message);

            Methods.sendAdminChat(commandSource, format, message);
        }
    }
}