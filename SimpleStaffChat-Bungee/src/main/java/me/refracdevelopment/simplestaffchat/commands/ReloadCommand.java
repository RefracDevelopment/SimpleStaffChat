package me.refracdevelopment.simplestaffchat.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    private final SimpleStaffChat plugin;

    public ReloadCommand(SimpleStaffChat plugin) {
        super("staffchatreload", "", "screload");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            RyMessageUtils.sendPluginMessage(commandSender, "no-permission");
            return;
        }

        reloadFiles();
        RyMessageUtils.sendPluginMessage(commandSender, "reload");
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
        plugin.getServers().loadConfig();

        RyMessageUtils.sendConsole(true, "&aReloaded all files.");
    }
}