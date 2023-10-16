package me.refracdevelopment.simplestaffchat.spigot.commands.devchat;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DevHideCommand extends Command {

    private final SimpleStaffChat plugin;

    public DevHideCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().DEV_HIDE_COMMAND, "", plugin.getCommands().DEV_HIDE_COMMAND_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!plugin.getCommands().DEV_HIDE_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!(commandSender instanceof Player)) {
            locale.sendMessage(commandSender, "no-console");
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission(plugin.getCommands().DEV_HIDE_COMMAND_PERMISSION)) {
            locale.sendMessage(player, "no-permission");
            return true;
        }

        plugin.getMethods().hideDevChat(player);
        return true;
    }
}