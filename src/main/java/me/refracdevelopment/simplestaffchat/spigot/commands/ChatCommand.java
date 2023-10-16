package me.refracdevelopment.simplestaffchat.spigot.commands;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public ChatCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().CHAT_COMMAND, "", plugin.getCommands().CHAT_COMMAND_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!plugin.getCommands().CHAT_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!(sender instanceof Player)) {
            locale.sendMessage(sender, "no-console");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            locale.sendMessage(player, "no-permission");
            return true;
        }

        switch (args[0]) {
            case "staff":
                plugin.getMethods().toggleStaffChat(player);
                break;
            case "admin":
                plugin.getMethods().toggleAdminChat(player);
                break;
            case "dev":
                plugin.getMethods().toggleDevChat(player);
                break;
            case "all":
                plugin.getMethods().toggleAllChat(player);
                break;
        }
        return true;
    }
}