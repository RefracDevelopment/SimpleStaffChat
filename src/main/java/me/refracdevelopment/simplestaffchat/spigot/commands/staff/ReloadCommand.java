/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 RefracDevelopment
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package me.refracdevelopment.simplestaffchat.spigot.commands.staff;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.Command;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super(Config.COMMANDS_RELOAD_COMMAND.toString(), Config.COMMANDS_RELOAD_ALIASES.toList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Config.COMMANDS_RELOAD_ENABLED.toBoolean()) return true;

        if (!sender.hasPermission(Permissions.STAFFCHAT_RELOAD)) {
            Color.sendMessage(sender, Config.MESSAGES_NO_PERMISSION.toString(), true, true);
            return true;
        }

        SimpleStaffChat.getInstance().getConfigFile().load();
        Color.sendMessage(sender, Config.MESSAGES_RELOAD.toString(), true, true);
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}