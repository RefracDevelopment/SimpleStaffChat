package me.refracdevelopment.simplestaffchat.command.commands;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    private final SimpleStaffChat plugin;

    public ReloadCommand(SimpleStaffChat plugin) {
        super("staffchatreload", "", "screload");
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission("simplestaffchat.command.reload")) {
            Color.sendMessage(sender, "no-permission");
            return true;
        }

        reloadFiles();
        Color.sendMessage(sender, "reload");
        return true;
    }


    private void reloadFiles() {
        this.plugin.getConfigFile().reload();
        this.plugin.getCommandsFile().reload();
        this.plugin.getDiscordFile().reload();
        this.plugin.getLocaleFile().reload();

        this.plugin.getSettings().loadConfig();
        this.plugin.getCommands().loadConfig();
        this.plugin.getDiscord().loadConfig();

        Color.log("&c==========================================");
        Color.log("&aAll files have been reloaded correctly!");
        Color.log("&c==========================================");
    }
}