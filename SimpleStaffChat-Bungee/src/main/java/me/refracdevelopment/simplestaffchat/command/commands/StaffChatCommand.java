package me.refracdevelopment.simplestaffchat.command.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!commandSender.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            RyMessageUtils.sendSender(commandSender, "no-permission");
            return;
        }

        if (args.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().STAFFCHAT_FORMAT
                    .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%player%", commandSender.getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_STAFFCHAT_FORMAT
                    .replace("%server%", "N/A")
                    .replace("%player%", commandSender.getName())
                    .replace("%message%", message);

            Methods.sendStaffChat(commandSender, format, message);
        }
    }
}