package me.refracdevelopment.simplestaffchat.spigot.commands.adminchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Config;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminChatCommand extends Command {

    public AdminChatCommand() {
        super(Commands.ADMINCHAT_COMMAND, "", Commands.ADMINCHAT_ALIAS);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Commands.ADMINCHAT_COMMAND_ENABLED) return false;

        String message = Joiner.on(" ").join(args);

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? Config.ADMINCHAT_FORMAT.replace("%message%", message) :
                    Config.CONSOLE_ADMINCHAT_FORMAT.replace("%message%", message);

            Methods.sendAdminChat(sender, format);
        }
        return true;
    }
}