package me.refracdevelopment.simplestaffchat.bungee.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;

@CommandAlias("staffchatversion|scversion")
@CommandPermission(Permissions.STAFFCHAT_VERSION)
@Description("Allows you to toggle staffchat.")
public class VersionCommand extends BaseCommand {

    @Default
    public void execute(CommandSender sender) {

    }
}