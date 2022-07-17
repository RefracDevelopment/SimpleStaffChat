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
package me.refracdevelopment.simplestaffchat.spigot.commands.dev;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.files.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CommandAlias("devchattoggle")
@Description("Toggle sending messages to your developers privately")
@CommandPermission(Permissions.DEVCHAT_TOGGLE)
@Conditions("noconsole")
public class DevToggleCommand extends BaseCommand {
    public static List<UUID> indc = new ArrayList<>();

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        if (!Config.DEVCHAT_TOGGLE_ENABLED) return;

        Player player = (Player) sender;

        if (indc.contains(player.getUniqueId())) {
            indc.remove(player.getUniqueId());
            Color.sendMessage(player, Config.DEVCHAT_TOGGLE_OFF, true, true);
        } else {
            indc.add(player.getUniqueId());
            Color.sendMessage(player, Config.DEVCHAT_TOGGLE_ON, true, true);
        }
    }
}
