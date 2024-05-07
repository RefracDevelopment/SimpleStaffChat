package me.refracdevelopment.simplestaffchat.command.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DevChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().DEVCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().DEVCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!commandSender.hasPermission(plugin.getCommands().DEVCHAT_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(commandSender, "no-permission");
            return;
        }

        if (args.length >= 1) {
            String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().DEVCHAT_FORMAT
                    .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                    .replace("%player%", commandSender.getName())
                    .replace("%message%", message) : plugin.getConfig().CONSOLE_DEVCHAT_FORMAT
                    .replace("%server%", "N/A")
                    .replace("%player%", commandSender.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(commandSender, format, message);
        }
    }
}