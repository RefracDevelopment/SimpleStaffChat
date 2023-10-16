package me.refracdevelopment.simplestaffchat.bungee.commands;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    private final BungeeStaffChat plugin;

    public ReloadCommand(BungeeStaffChat plugin) {
        super("staffchatreload", "", "screload");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(plugin.getPermissions().STAFFCHAT_RELOAD)) {
            plugin.getColor().sendMessage(commandSender, plugin.getConfig().NO_PERMISSION);
            return;
        }

        plugin.reloadFiles();
        plugin.getColor().sendMessage(commandSender, plugin.getConfig().RELOAD);
    }
}