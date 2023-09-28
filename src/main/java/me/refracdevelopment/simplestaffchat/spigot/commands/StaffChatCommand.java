package me.refracdevelopment.simplestaffchat.spigot.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Methods;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Placeholders;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        super(Commands.STAFFCHAT_COMMAND, "", Commands.STAFFCHAT_ALIAS);
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

            if (!sender.hasPermission(Permissions.STAFFCHAT_COMMAND)) {
                locale.sendMessage(sender, "no-permission");
                return true;
            }

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
                    locale.sendMessage(sender, "no-permission", Placeholders.setPlaceholders(sender));
                    return true;
                }

                Config.STAFFCHAT_MESSAGE.forEach(s -> locale.sendCustomMessage(sender, Placeholders.setPlaceholders(sender, s)));
            } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    Methods.toggleStaffChat(player);
                }
            } else {
                if (!sender.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    locale.sendMessage(sender, "no-permission", Placeholders.setPlaceholders(sender));
                    return true;
                }

                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "<g:#8A2387:#E94057:#F27121>SimpleStaffChat &8| &fAvailable Commands:".replace("|", "\u239F"));
                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "&d/" + Commands.STAFFCHAT_COMMAND + " <message> &7- Send staffchat messages.");
                locale.sendCustomMessage(sender, "&d/" + Commands.TOGGLE_COMMAND + " &7- Send staffchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + Commands.DEVCHAT_COMMAND + " <message> - Send adminchat messages.");
                locale.sendCustomMessage(sender, "&d/" + Commands.DEV_TOGGLE_COMMAND + " &7- Send adminchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + Commands.DEVCHAT_COMMAND + " <message> &7- Send devchat messages.");
                locale.sendCustomMessage(sender, "&d/" + Commands.DEV_TOGGLE_COMMAND + " &7- Send devchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + Commands.CHAT_COMMAND + " <type:staff|admin|dev> &7- Send chat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + Commands.RELOAD_COMMAND + " &7- Reload the config file.");
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}