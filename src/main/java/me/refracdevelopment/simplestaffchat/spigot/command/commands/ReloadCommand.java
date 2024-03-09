package me.refracdevelopment.simplestaffchat.spigot.command.commands;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
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
        if (!sender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            Color.sendMessage(sender, "no-permission");
            return true;
        }

        reloadFiles();
        Color.sendMessage(sender, "reload");
        return true;
    }

    private void reloadFiles() {
        // Files
        plugin.getConfigFile().reload();
        plugin.getCommandsFile().reload();
        plugin.getDiscordFile().reload();
        plugin.getLocaleFile().reload();

        // Caches
        plugin.getSettings().loadConfig();
        plugin.getCommands().loadConfig();
        plugin.getDiscord().loadConfig();

        Color.log("&c==========================================");
        Color.log("&aAll files have been reloaded correctly!");
        Color.log("&c==========================================");
    }
}