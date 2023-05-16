package me.refracdevelopment.simplestaffchat.spigot.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Placeholders;
import org.bukkit.command.CommandSender;

@CommandAlias("staffchatreload|screload")
@CommandPermission(Permissions.STAFFCHAT_RELOAD)
@Description("Reloads the config file")
public class ReloadCommand extends BaseCommand {

    private final SimpleStaffChat plugin;

    public ReloadCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Default
    public void onDefault(CommandSender sender) {
        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!sender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            locale.sendMessage(sender, "no-permission", Placeholders.setPlaceholders(sender));
            return;
        }

        plugin.reload();
        Config.loadConfig();
        locale.sendMessage(sender, "reload", Placeholders.setPlaceholders(sender));
    }
}