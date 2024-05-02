package me.refracdevelopment.simplestaffchat.command.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HideCommand extends Command {
    private final SimpleStaffChat plugin;

    public HideCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().HIDE_COMMAND_ALIASES.get(0), "", (String[]) (plugin.getCommands()).HIDE_COMMAND_ALIASES.toArray((Object[]) new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        // Make sure the sender is a player.
        if (!(sender instanceof Player player)) {
            Color.sendMessage(sender, "no-console");
            return true;
        }

        if (!player.hasPermission(this.plugin.getCommands().HIDE_COMMAND_PERMISSION)) {
            Color.sendMessage(player, "no-permission");
            return true;
        }

        if (args.length == 0) {
            Color.sendCustomMessage(player, "&c/" + getName() + " <staff|admin|dev|all>");
            return true;
        }

        switch (args[0]) {
            case "staff":
                Methods.hideStaffChat(player);
                break;
            case "admin":
                Methods.hideAdminChat(player);
                break;
            case "dev":
                Methods.hideDevChat(player);
                break;
            case "all":
                Methods.hideAll(player);
                break;
        }

        return true;
    }
}