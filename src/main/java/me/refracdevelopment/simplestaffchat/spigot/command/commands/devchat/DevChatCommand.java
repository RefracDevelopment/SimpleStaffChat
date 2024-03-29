package me.refracdevelopment.simplestaffchat.spigot.command.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DevChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevChatCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().DEVCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().DEVCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!sender.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(sender, "no-permission");
            return true;
        }

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? plugin.getSettings().DEVCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%message%", message) : plugin.getSettings().CONSOLE_DEVCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%message%", message);

            Methods.sendDevChat(sender, format, message);
        }
        return true;
    }
}