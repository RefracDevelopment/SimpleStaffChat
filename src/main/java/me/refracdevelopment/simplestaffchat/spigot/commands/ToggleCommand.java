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

    private final SimpleStaffChat plugin;

    public static List<UUID> insc = new ArrayList<>();

    public ToggleCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(CommandSender sender) {
        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        if (!(sender instanceof Player)) {
            locale.sendCommandMessage(sender, "no-console");
            return;
        }

        Player player = (Player) sender;

        if (insc.contains(player.getUniqueId())) {
            insc.remove(player.getUniqueId());
            locale.sendCommandMessage(player, "toggle-off", Placeholders.setPlaceholders(player));
        } else {
            insc.add(player.getUniqueId());
            locale.sendCommandMessage(player, "toggle-on", Placeholders.setPlaceholders(player));
        }
    }
}