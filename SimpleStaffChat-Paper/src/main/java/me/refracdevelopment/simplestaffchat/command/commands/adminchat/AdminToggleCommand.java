package me.refracdevelopment.simplestaffchat.command.commands.adminchat;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public AdminToggleCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().ADMIN_TOGGLE_COMMAND_ALIASES.get(0), "", (String[]) (plugin.getCommands()).ADMIN_TOGGLE_COMMAND_ALIASES.toArray((Object[]) new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Color.sendMessage(sender, "no-console");
            return true;
        }

        if (!player.hasPermission(this.plugin.getCommands().ADMIN_TOGGLE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, "no-permission");
            return true;
        }

        Methods.toggleAdminChat(player);

        return true;
    }
}