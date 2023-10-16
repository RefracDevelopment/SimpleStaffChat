package me.refracdevelopment.simplestaffchat.spigot.commands.devchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DevChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevChatCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().DEVCHAT_COMMAND, "", plugin.getCommands().DEVCHAT_COMMAND_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!plugin.getCommands().DEVCHAT_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        String message = Joiner.on(" ").join(args);

        if (!sender.hasPermission(plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            locale.sendMessage(sender, "no-permission");
            return true;
        }

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? plugin.getSettings().DEVCHAT_FORMAT.replace("%message%", message) :
                    plugin.getSettings().CONSOLE_DEVCHAT_FORMAT.replace("%message%", message);

            plugin.getMethods().sendDevChat(sender, format);
        }
        return true;
    }
}