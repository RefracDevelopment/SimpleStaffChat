package me.refracdevelopment.simplestaffchat.spigot.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        super(Commands.STAFFCHAT_COMMAND, Permissions.STAFFCHAT_COMMAND, Commands.STAFFCHAT_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Commands.STAFFCHAT_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        String message = Joiner.on(" ").join(args);

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? Config.MINECRAFT_FORMAT.replace("%message%", message) :
                    Config.CONSOLE_FORMAT.replace("%message%", message);

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(sender, format));
                }
            }
            if (Config.BUNGEECORD && sender instanceof Player) {
                Player player = (Player) sender;
                plugin.getPluginMessage().sendStaffChat(player, Color.translate(sender, format));
            }
            Color.log2(Color.translate(sender, format));
        } else {
            if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("custom")) {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    locale.sendMessage(sender, "usage", Placeholders.setPlaceholders(sender));
                    return true;
                }

                Config.STAFFCHAT_MESSAGE.forEach(s -> locale.sendCustomMessage(sender, Placeholders.setPlaceholders(sender, s)));
            } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        Config.STAFFCHAT_MESSAGE.forEach(s -> locale.sendCustomMessage(sender, Placeholders.setPlaceholders(sender, s)));
                        return true;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        locale.sendMessage(sender, "toggle-off", Placeholders.setPlaceholders(sender));
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        locale.sendMessage(sender, "toggle-on", Placeholders.setPlaceholders(sender));
                    }
                }
            } else {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    locale.sendMessage(sender, "usage", Placeholders.setPlaceholders(sender));
                    return true;
                }

                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "<g:#8A2387:#E94057:#F27121>SimpleStaffChat &8| &fAvailable Commands:".replace("|", "\u239F"));
                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "&8- &d/staffchat <message> &7- Send staffchat messages.");
                locale.sendCustomMessage(sender, "&8- &d/staffchattoggle &7- Send staffchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&8- &d/staffchatreload &7- Reload the config file.");
                locale.sendCustomMessage(sender, "");
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}