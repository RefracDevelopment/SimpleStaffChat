package me.refracdevelopment.simplestaffchat.bungee.commands;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Config;
import me.refracdevelopment.simplestaffchat.bungee.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    private final BungeeStaffChat plugin;

    public ReloadCommand(BungeeStaffChat plugin) {
        super(Commands.RELOAD_COMMAND, "", Commands.RELOAD_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!Commands.RELOAD_COMMAND_ENABLED) return;

        if (!commandSender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            Color.sendMessage(commandSender, Config.NO_PERMISSION);
            return;
        }

        plugin.getConfigFile().load();
        plugin.getCommandsFile().load();
        plugin.getDiscordFile().load();
        Config.loadConfig();
        Commands.loadConfig();
        Discord.loadConfig();
        Color.sendMessage(commandSender, Config.RELOAD);
    }
}