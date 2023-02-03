package me.refracdevelopment.simplestaffchat.spigot.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.listeners.PluginMessage;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.config.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("staffchat|sc")
@CommandPermission(Permissions.STAFFCHAT_COMMAND)
@Description("Send messages to your staff privately")
public class StaffChatCommand extends BaseCommand {

    private final SimpleStaffChat plugin;
    private final PluginMessage pluginMessage = new PluginMessage(SimpleStaffChat.getInstance());

    public StaffChatCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandCompletion("@players")
    public void execute(CommandSender sender, String[] args) {
        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        String message = Joiner.on(" ").join(args);

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? Config.MINECRAFT_FORMAT.replace("%message%", message) :
                    Config.CONSOLE_FORMAT.replace("%message%", message);

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    locale.sendCustomMessage(p, Placeholders.setPlaceholders(sender, format));
                    if (Config.VELOCITY) {
                        pluginMessage.sendMessage(Color.translate(sender, format));
                    }
                }
            }
            Color.log2(Placeholders.setPlaceholders(sender, format));
        } else {
            if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("custom")) {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    locale.sendCommandMessage(sender, "usage", Placeholders.setPlaceholders(sender));
                    return;
                }

                Config.STAFFCHAT_MESSAGE.forEach(s -> locale.sendCustomMessage(sender, Placeholders.setPlaceholders(sender, s)));
            } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        Config.STAFFCHAT_MESSAGE.forEach(s -> locale.sendCustomMessage(sender, Placeholders.setPlaceholders(sender, s)));
                        return;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        locale.sendCommandMessage(sender, "toggle-off", Placeholders.setPlaceholders(sender));
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        locale.sendCommandMessage(sender, "toggle-on", Placeholders.setPlaceholders(sender));
                    }
                }
            } else {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    locale.sendCommandMessage(sender, "usage", Placeholders.setPlaceholders(sender));
                    return;
                }

                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, Placeholders.setPlaceholders(sender, "<g:#8A2387:#E94057:#F27121>SimpleStaffChat &8| &fAvailable Commands:"));
                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "&8- &d/staffchat <message> &7- Send staffchat messages.");
                locale.sendCustomMessage(sender, "&8- &d/staffchattoggle &7- Send staffchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&8- &d/staffchatreload &7- Reload the config file.");
                locale.sendCustomMessage(sender, "");
            }
        }
    }
}