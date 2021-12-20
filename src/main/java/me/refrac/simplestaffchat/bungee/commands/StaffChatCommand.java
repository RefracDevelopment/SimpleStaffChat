/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 RefracDevelopment
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
package me.refrac.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refrac.simplestaffchat.bungee.utilities.files.Config;
import me.refrac.simplestaffchat.bungee.utilities.chat.Color;
import me.refrac.simplestaffchat.shared.Permissions;
import me.refrac.simplestaffchat.shared.Settings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super(Config.STAFFCHAT_COMMAND, "", Config.STAFFCHAT_ALIAS);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Config.STAFFCHAT_ENABLED) return;
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length >= 1) {
            if (!player.hasPermission(Permissions.STAFFCHAT_USE)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true);
                return;
            }

            String message = Joiner.on(" ").join(args);
            String format = Config.STAFFCHAT_FORMAT.replace("%server%", player.getServer().getInfo().getName())
                    .replace("%message%", message);

            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, format));
                }
            }

            ProxyServer.getInstance().getConsole().sendMessage(Color.translate(player, format));
        } else {
            if (!player.hasPermission(Permissions.STAFFCHAT_USE)) {
                Color.sendMessage(player, Config.NO_PERMISSION, true);
                return;
            }

            Color.sendMessage(player, "", true);
            Color.sendMessage(player, "&e&lRunning " + Settings.getName + " &bv" + Settings.getVersion, true);
            Color.sendMessage(player, "", true);
            Color.sendMessage(player, "&e&lUsage: /sc <message>", true);
            Color.sendMessage(player, "&e&lUsage: /sctoggle", true);
            Color.sendMessage(player, "&e&lUsage: /screload", true);
            Color.sendMessage(player, "", true);
        }
    }
}