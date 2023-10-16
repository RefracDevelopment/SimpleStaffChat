package me.refracdevelopment.simplestaffchat.spigot.commands;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    private final SimpleStaffChat plugin;

    public ReloadCommand(SimpleStaffChat plugin) {
        super(plugin, "staffchatreload", "", "screload");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!sender.hasPermission(plugin.getPermissions().STAFFCHAT_RELOAD)) {
            locale.sendMessage(sender, "no-permission");
            return true;
        }

        plugin.reload();
        plugin.reloadFiles();
        locale.sendMessage(sender, "reload", plugin.getPlaceholders().setPlaceholders(sender));
        return true;
    }
}