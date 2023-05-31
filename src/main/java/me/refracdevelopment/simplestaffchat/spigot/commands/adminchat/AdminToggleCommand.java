package me.refracdevelopment.simplestaffchat.spigot.commands.adminchat;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.Commands;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Placeholders;
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
        super(Commands.ADMIN_TOGGLE_COMMAND, Permissions.ADMINCHAT_TOGGLE, Commands.ADMIN_TOGGLE_ALIAS);
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

        if (!sender.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
            locale.sendMessage(sender, "no-permission");
            return true;
        }

        if (inac.contains(player.getUniqueId())) {
            inac.remove(player.getUniqueId());
            locale.sendMessage(player, "adminchat-toggle-off", Placeholders.setPlaceholders(player));
        } else {
            inac.add(player.getUniqueId());
            locale.sendMessage(player, "adminchat-toggle-on", Placeholders.setPlaceholders(player));
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}