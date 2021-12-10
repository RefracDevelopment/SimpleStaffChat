/*
 * Copyright (c) Refrac
 * If you have any questions please join my discord https://discord.gg/jVnmm7QnQU
 */
package me.refrac.simplestaffchat.spigot.commands;

import me.refrac.simplestaffchat.spigot.SimpleStaffChat;
import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.spigot.utilities.files.Config;
import me.refrac.simplestaffchat.spigot.utilities.files.Files;
import me.refrac.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Permissions.STAFFCHAT_ADMIN)) {
            Color.sendMessage(sender, Config.NO_PERMISSION, true);
            return true;
        }

        Files.reloadFiles(SimpleStaffChat.getInstance());
        Config.loadConfig();
        Color.sendMessage(sender, Config.RELOAD, true);
        return true;
    }
}