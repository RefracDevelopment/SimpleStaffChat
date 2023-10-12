package me.refracdevelopment.simplestaffchat.spigot.commands.adminchat;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public static List<UUID> inac = new ArrayList<>();

    public AdminToggleCommand(SimpleStaffChat plugin) {
        super(Commands.ADMIN_TOGGLE_COMMAND, "", Commands.ADMIN_TOGGLE_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Commands.ADMIN_TOGGLE_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!(sender instanceof Player)) {
            locale.sendMessage(sender, "no-console");
            return true;
        }

        Player player = (Player) sender;

        Methods.toggleAdminChat(player);
        return true;
    }
}