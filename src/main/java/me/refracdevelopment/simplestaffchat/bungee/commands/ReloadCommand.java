package me.refracdevelopment.simplestaffchat.bungee.commands;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
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
        if (!commandSender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            Color.sendMessage(commandSender, "no-permission");
            return;
        }

        plugin.reloadFiles();
        Color.sendMessage(commandSender, "reload");
    }
}