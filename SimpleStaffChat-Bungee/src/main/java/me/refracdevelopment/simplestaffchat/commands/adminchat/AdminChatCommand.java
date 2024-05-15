package me.refracdevelopment.simplestaffchat.commands.adminchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AdminChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public AdminChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().ADMINCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().ADMINCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!commandSender.hasPermission(plugin.getCommands().ADMINCHAT_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(commandSender, "no-permission");
            return;
        }

        if (!(args.length >= 1)) {
            RyMessageUtils.sendSender(commandSender, "&cUsage: /" + this.getName() + " <message>");
            return;
        }

        String format = (commandSender instanceof ProxiedPlayer) ? plugin.getConfig().ADMINCHAT_FORMAT
                .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName())
                .replace("%player%", commandSender.getName())
                .replace("%message%", message) : plugin.getConfig().CONSOLE_ADMINCHAT_FORMAT
                .replace("%server%", "N/A")
                .replace("%player%", commandSender.getName())
                .replace("%message%", message);

        Methods.sendAdminChat(commandSender, format, message);
    }
}