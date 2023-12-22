package me.refracdevelopment.simplestaffchat.spigot.command.commands;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HideCommand extends Command {

    private final SimpleStaffChat plugin;

    public HideCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().HIDE_COMMAND_ALIASES.get(0), "", plugin.getCommands().HIDE_COMMAND_ALIASES.toArray(new String[0]));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            Color.sendMessage(commandSender, "no-console");
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission(plugin.getCommands().HIDE_COMMAND_PERMISSION)) {
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