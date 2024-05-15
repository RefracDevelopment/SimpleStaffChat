package me.refracdevelopment.simplestaffchat.command.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DevChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().DEVCHAT_COMMAND_ALIASES.get(0), "", (String[]) (plugin.getCommands()).DEVCHAT_COMMAND_ALIASES.toArray((Object[]) new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!sender.hasPermission(this.plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(sender, "no-permission");
            return true;
        }

        if (!(args.length >= 1)) {
            RyMessageUtils.sendSender(sender, "&cUsage: /" + this.getName() + " <message>");
            return true;
        }

        String format = (sender instanceof Player) ? this.plugin.getSettings().DEVCHAT_FORMAT
                .replace("%server%", this.plugin.getSettings().SERVER_NAME)
                .replace("%player%", sender.getName())
                .replace("%message%", message) : this.plugin.getSettings().CONSOLE_DEVCHAT_FORMAT
                .replace("%server%", this.plugin.getSettings().SERVER_NAME)
                .replace("%player%", sender.getName())
                .replace("%message%", message);

        Methods.sendDevChat(sender, format, message);

        return true;
    }
}