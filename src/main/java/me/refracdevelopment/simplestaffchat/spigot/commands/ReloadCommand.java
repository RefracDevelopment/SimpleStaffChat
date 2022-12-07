package me.refracdevelopment.simplestaffchat.spigot.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.config.Config;
import org.bukkit.command.CommandSender;

@CommandAlias("staffchatreload|screload")
@CommandPermission(Permissions.STAFFCHAT_RELOAD)
@Description("Reloads the config file")
public class ReloadCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (!sender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            locale.sendMessage(sender, "no-permission", Placeholders.setPlaceholders(sender));
            return;
        }

        SimpleStaffChat.getInstance().reload();
        Config.loadConfig();
        locale.sendMessage(sender, "reload", Placeholders.setPlaceholders(sender));
    }
}