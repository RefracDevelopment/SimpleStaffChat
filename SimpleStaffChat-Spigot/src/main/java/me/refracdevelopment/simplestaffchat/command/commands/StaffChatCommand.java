package me.refracdevelopment.simplestaffchat.command.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.get(0), "", (String[]) (plugin.getCommands()).STAFFCHAT_COMMAND_ALIASES.toArray((Object[]) new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!sender.hasPermission(this.plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(sender, "no-permission");
            return true;
        }

        if (!(args.length >= 1)) {
            RyMessageUtils.sendSender(sender, "&cUsage: /" + this.getName() + " <message>");
            return true;
        }

        String format = (sender instanceof org.bukkit.entity.Player) ? this.plugin.getSettings().STAFFCHAT_FORMAT
                .replace("%server%", this.plugin.getSettings().SERVER_NAME)
                .replace("%player%", sender.getName())
                .replace("%message%", message) : this.plugin.getSettings().CONSOLE_STAFFCHAT_FORMAT
                .replace("%server%", (this.plugin.getSettings()).SERVER_NAME)
                .replace("%player%", sender.getName())
                .replace("%message%", message);

        Methods.sendStaffChat(sender, format, message);

        return true;
    }
}