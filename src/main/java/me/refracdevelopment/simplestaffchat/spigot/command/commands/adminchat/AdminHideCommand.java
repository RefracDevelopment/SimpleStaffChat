package me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminHideCommand extends Command {

    private final SimpleStaffChat plugin;

    public AdminHideCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().ADMIN_HIDE_COMMAND_ALIASES.get(0), "", plugin.getCommands().ADMIN_HIDE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!plugin.getCommands().ADMIN_TOGGLE_COMMAND_ENABLED) return false;

        

        if (!(commandSender instanceof Player)) {
            plugin.getColor().sendMessage(commandSender, "no-console");
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission(plugin.getCommands().ADMIN_HIDE_COMMAND_PERMISSION)) {
            plugin.getColor().sendMessage(player, "no-permission");
            return true;
        }

        plugin.getMethods().hideAdminChat(player);
        return true;
    }
}