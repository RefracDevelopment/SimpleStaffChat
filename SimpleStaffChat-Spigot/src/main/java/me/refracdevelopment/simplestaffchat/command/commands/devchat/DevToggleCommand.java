package me.refracdevelopment.simplestaffchat.command.commands.devchat;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DevToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevToggleCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().DEV_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().DEV_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        // Make sure the sender is a player.
        if (!(sender instanceof Player player)) {
            RyMessageUtils.sendPluginMessage(sender, "no-console");
            return true;
        }

        if (!player.hasPermission(this.plugin.getCommands().DEV_TOGGLE_COMMAND_PERMISSION)) {
            RyMessageUtils.sendPluginMessage(player, "no-permission");
            return true;
        }

        Methods.toggleDevChat(player);
        return true;
    }
}