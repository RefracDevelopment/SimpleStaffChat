package me.refracdevelopment.simplestaffchat.command.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public ToggleCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().STAFF_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().STAFF_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Color.sendMessage(sender, "no-console");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(this.plugin.getCommands().STAFF_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, "no-permission");
            return true;
        }

        Methods.toggleStaffChat(player);
        return true;
    }
}