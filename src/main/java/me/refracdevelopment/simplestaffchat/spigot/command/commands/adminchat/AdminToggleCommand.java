package me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public AdminToggleCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().ADMIN_TOGGLE_COMMAND_ALIASES.get(0), "", plugin.getCommands().ADMIN_TOGGLE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Color.sendMessage(sender, "no-console");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, "no-permission");
            return true;
        }

        Methods.toggleAdminChat(player);
        return true;
    }
}