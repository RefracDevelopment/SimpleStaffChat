package me.refracdevelopment.simplestaffchat.spigot.command.commands;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
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
        if (!sender.hasPermission(plugin.getPermissions().STAFFCHAT_RELOAD)) {
            plugin.getColor().sendMessage(sender, "no-permission");
            return true;
        }

        plugin.reloadFiles();
        plugin.getColor().sendMessage(sender, "reload");
        return true;
    }
}