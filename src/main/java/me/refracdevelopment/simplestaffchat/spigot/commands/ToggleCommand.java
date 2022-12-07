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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CommandAlias("staffchattoggle|sctoggle")
@CommandPermission(Permissions.STAFFCHAT_TOGGLE)
@Description("Allows you to toggle staffchat.")
public class ToggleCommand extends BaseCommand {

    public static List<UUID> insc = new ArrayList<>();

    @Default
    public void execute(CommandSender sender) {
        Player player = (Player) sender;

        final LocaleManager locale = SimpleStaffChat.getInstance().getManager(LocaleManager.class);

        if (insc.contains(player.getUniqueId())) {
            insc.remove(player.getUniqueId());
            locale.sendMessage(player, "toggle-off", Placeholders.setPlaceholders(player));
        } else {
            insc.add(player.getUniqueId());
            locale.sendMessage(player, "toggle-on", Placeholders.setPlaceholders(player));
        }
    }
}