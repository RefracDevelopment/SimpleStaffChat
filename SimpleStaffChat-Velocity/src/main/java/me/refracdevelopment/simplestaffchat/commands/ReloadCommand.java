package me.refracdevelopment.simplestaffchat.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Permissions;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;

public class ReloadCommand implements SimpleCommand {

    private final SimpleStaffChat plugin;

    public ReloadCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource commandSource = invocation.source();

        if (!commandSource.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            RyMessageUtils.sendPluginMessage(commandSource, "no-permission");
            return;
        }

        reloadFiles();
        RyMessageUtils.sendPluginMessage(commandSource, "reload");
    }

    private void reloadFiles() {
        // Files
        plugin.getConfigFile().reload();
        plugin.getCommandsFile().reload();
        plugin.getDiscordFile().reload();
        plugin.getLocaleFile().reload();

        // Cache
        plugin.getConfig().loadConfig();
        plugin.getCommands().loadConfig();
        plugin.getDiscord().loadConfig();

        RyMessageUtils.sendConsole(true, "&c==========================================");
        RyMessageUtils.sendConsole(true, "&aAll files have been reloaded correctly!");
        RyMessageUtils.sendConsole(true, "&c==========================================");
    }
}