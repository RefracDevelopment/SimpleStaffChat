package me.refracdevelopment.simplestaffchat.spigot.commands;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Config;
import me.refracdevelopment.simplestaffchat.spigot.config.cache.Discord;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    private final SimpleStaffChat plugin;

    public ReloadCommand(SimpleStaffChat plugin) {
        super(Commands.RELOAD_COMMAND, "", Commands.RELOAD_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Commands.RELOAD_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!sender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            locale.sendMessage(sender, "no-permission");
            return true;
        }

        plugin.reload();
        plugin.getCommandsFile().load();
        plugin.getDiscordFile().load();
        Config.loadConfig();
        Commands.loadConfig();
        Discord.loadConfig();
        locale.sendMessage(sender, "reload", Placeholders.setPlaceholders(sender));
        return true;
    }
}