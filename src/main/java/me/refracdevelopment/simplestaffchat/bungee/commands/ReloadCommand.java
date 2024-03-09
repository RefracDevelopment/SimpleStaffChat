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
    public void execute(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            Color.sendMessage(commandSender, "no-permission");
            return;
        }

        reloadFiles();
        Color.sendMessage(commandSender, "reload");
    }

    private void reloadFiles() {
        // Files
        plugin.getConfigFile().reload();
        plugin.getCommandsFile().reload();
        plugin.getDiscordFile().reload();
        plugin.getLocaleFile().reload();

        // Caches
        plugin.getConfig().loadConfig();
        plugin.getCommands().loadConfig();
        plugin.getDiscord().loadConfig();

        Color.log("&c==========================================");
        Color.log("&eAll files have been reloaded correctly!");
        Color.log("&c==========================================");
    }
}