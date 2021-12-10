/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.bungee.commands;

import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.files.Files;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import me.refrac.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("staffchatreload", "", "screload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (!player.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true, false);
                return;
            }

            Files.loadFiles();
            Config.loadConfig();
            Color.sendMessage(player, Config.RELOAD, true, true);
        } else {
            if (!sender.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
                Color.sendMessage(sender, Config.NO_PERMISSION, true);
                return;
            }

            Files.loadFiles();
            Config.loadConfig();
            Color.sendMessage(sender, Config.RELOAD, true);
        }
    }
}