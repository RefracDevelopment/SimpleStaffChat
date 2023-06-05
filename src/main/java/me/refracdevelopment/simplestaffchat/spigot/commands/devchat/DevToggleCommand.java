package me.refracdevelopment.simplestaffchat.spigot.commands.devchat;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.config.Commands;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DevToggleCommand extends Command {

    private final SimpleStaffChat plugin;

    public static List<UUID> indc = new ArrayList<>();

    public DevToggleCommand(SimpleStaffChat plugin) {
        super(Commands.DEV_TOGGLE_COMMAND, "", Commands.DEV_TOGGLE_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Commands.DEV_TOGGLE_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!(sender instanceof Player)) {
            locale.sendMessage(sender, "no-console");
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission(Permissions.DEVCHAT_TOGGLE)) {
            locale.sendMessage(sender, "no-permission");
            return true;
        }

        if (indc.contains(player.getUniqueId())) {
            indc.remove(player.getUniqueId());
            locale.sendMessage(player, "devchat-toggle-off", Placeholders.setPlaceholders(player));
        } else {
            if (AdminToggleCommand.inac.contains(player.getUniqueId()) || ToggleCommand.insc.contains(player.getUniqueId())) {
                AdminToggleCommand.inac.remove(player.getUniqueId());
                ToggleCommand.insc.remove(player.getUniqueId());
            }
            indc.add(player.getUniqueId());
            locale.sendMessage(player, "devchat-toggle-on", Placeholders.setPlaceholders(player));
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}