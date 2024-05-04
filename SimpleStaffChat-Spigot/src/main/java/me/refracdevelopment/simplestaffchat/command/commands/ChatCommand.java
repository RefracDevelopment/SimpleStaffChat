package me.refracdevelopment.simplestaffchat.command.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public ChatCommand(SimpleStaffChat plugin) {
        super(plugin.getCommands().CHAT_COMMAND_ALIASES.get(0), "", (String[]) (plugin.getCommands()).CHAT_COMMAND_ALIASES.toArray((Object[]) new String[0]));
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        // Make sure the sender is a player.
        if (!(sender instanceof Player)) {
            Color.sendMessage(sender, "no-console");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(this.plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            Color.sendMessage(player, "no-permission");
            return true;
        }

        if (args.length == 0) {
            Color.sendCustomMessage(player, "&c/" + getName() + " <staff|admin|dev|all>");
            return true;
        }

        switch (args[0]) {
            case "staff":
                Methods.toggleStaffChat(player);
                break;
            case "admin":
                Methods.toggleAdminChat(player);
                break;
            case "dev":
                Methods.toggleDevChat(player);
                break;
            case "all":
                Methods.toggleAllChat(player);
                break;
        }

        return true;
    }
}