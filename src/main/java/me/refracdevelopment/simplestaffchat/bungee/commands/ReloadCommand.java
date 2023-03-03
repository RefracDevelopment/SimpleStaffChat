package me.refracdevelopment.simplestaffchat.bungee.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.config.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;

@CommandAlias("staffchatreload|screload")
@CommandPermission(Permissions.STAFFCHAT_RELOAD)
@Description("Reloads the config file.")
public class ReloadCommand extends BaseCommand {

    private final BungeeStaffChat plugin;

    public ReloadCommand(BungeeStaffChat plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(CommandSender sender) {
        plugin.getConfigFile().load();
        Color.sendMessage(sender, Config.MESSAGES_RELOAD.toString(), true);
    }
}
