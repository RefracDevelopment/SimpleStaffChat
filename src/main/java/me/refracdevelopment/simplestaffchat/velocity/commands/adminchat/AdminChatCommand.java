package me.refracdevelopment.simplestaffchat.velocity.commands.adminchat;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;

public class AdminChatCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public AdminChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.ADMINCHAT_COMMAND_ENABLED.getBoolean()) return;

        CommandSource commandSource = invocation.source();

        if (invocation.arguments().length >= 1) {
            String format;
            String message = Joiner.on(" ").join(invocation.arguments());

            format = (commandSource instanceof Player) ? Config.ADMINCHAT_FORMAT.getString().replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_ADMINCHAT_FORMAT.getString().replace("%message%", message);

            if (!commandSource.hasPermission(Permissions.ADMINCHAT_COMMAND)) {
                Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
                return;
            }

            for (Player p : plugin.getServer().getAllPlayers()) {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(commandSource, format));
                }
            }
            Color.log2(Color.translate(commandSource, format));
        }
    }
}