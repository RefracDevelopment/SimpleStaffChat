package me.refracdevelopment.simplestaffchat.spigot.command.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.get(0), "", plugin.getCommands().STAFFCHAT_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String message = Joiner.on(" ").join(args);

        if (!sender.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(sender, "no-permission");
            return true;
        }

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? plugin.getSettings().STAFFCHAT_FORMAT
                    .replace("%server%", plugin.getSettings().SERVER_NAME)
                    .replace("%player%", sender.getName())
                    .replace("%message%", message) : plugin.getSettings().CONSOLE_STAFFCHAT_FORMAT
                            .replace("%server%", plugin.getSettings().SERVER_NAME)
                            .replace("%player%", sender.getName())
                            .replace("%message%", message);

            Methods.sendStaffChat(sender, format, message);
        } else {
            if (plugin.getSettings().STAFFCHAT_OUTPUT.equalsIgnoreCase("default") ||
                    plugin.getSettings().STAFFCHAT_OUTPUT.equalsIgnoreCase("custom"))  {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(sender, "no-permission");
                    return true;
                }

                plugin.getSettings().STAFFCHAT_MESSAGE.forEach(s -> Color.sendCustomMessage(sender, Color.translate(sender, s)));
            } else if (plugin.getSettings().STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    Methods.toggleStaffChat(player);
                }
            }
        }
        return true;
    }
}