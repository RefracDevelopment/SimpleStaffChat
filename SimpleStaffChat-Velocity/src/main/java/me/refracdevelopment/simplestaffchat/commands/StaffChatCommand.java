package me.refracdevelopment.simplestaffchat.commands;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;

public final class StaffChatCommand implements SimpleCommand {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        String message = Joiner.on(" ").join(invocation.arguments());

        if (!commandSource.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(commandSource, "no-permission");
            return;
        }

        if (invocation.arguments().length >= 1) {
            String format = (commandSource instanceof Player) ? plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%player%", ((Player) commandSource).getUsername())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_STAFFCHAT_FORMAT
                    .replace("%server%", plugin.getConfig().SERVER_NAME)
                    .replace("%player%", "Console")
                    .replace("%message%", message);

            Methods.sendStaffChat(commandSource, format, message);
        }
    }
}