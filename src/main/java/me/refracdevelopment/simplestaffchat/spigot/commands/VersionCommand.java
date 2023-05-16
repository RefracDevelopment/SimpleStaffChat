package me.refracdevelopment.simplestaffchat.spigot.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import dev.rosewood.rosegarden.utils.StringPlaceholders;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import org.bukkit.command.CommandSender;

public class VersionCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        String baseColor = locale.getLocaleMessage("base-command-color");
        locale.sendCustomMessage(sender, baseColor + "Running <g:#8A2387:#E94057:#F27121>" + SimpleStaffChat.getInstance().getDescription().getName() + baseColor + " v" + SimpleStaffChat.getInstance().getDescription().getVersion());
        locale.sendCustomMessage(sender, baseColor + "Plugin created by: <g:#41E0F0:#FF8DCE>" + SimpleStaffChat.getInstance().getDescription().getAuthors().get(0));
        locale.sendSimpleMessage(sender, "base-command-help", StringPlaceholders.of("cmd", "staffchat"));
    }
}
