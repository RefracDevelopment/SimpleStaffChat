package me.refracdevelopment.simplestaffchat.spigot.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public StaffChatCommand(SimpleStaffChat plugin) {
        super(plugin, plugin.getCommands().STAFFCHAT_COMMAND, "", plugin.getCommands().STAFFCHAT_COMMAND_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!plugin.getCommands().STAFFCHAT_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        String message = Joiner.on(" ").join(args);

        if (!sender.hasPermission(plugin.getCommands().STAFFCHAT_COMMAND_PERMISSION)) {
            locale.sendMessage(sender, "no-permission");
            return true;
        }

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? plugin.getSettings().MINECRAFT_FORMAT.replace("%message%", message) :
                    plugin.getSettings().CONSOLE_FORMAT.replace("%message%", message);

            plugin.getMethods().sendStaffChat(sender, format);
        } else {
            if (plugin.getSettings().STAFFCHAT_OUTPUT.equalsIgnoreCase("custom")) {
                if (!sender.hasPermission(plugin.getPermissions().STAFFCHAT_HELP)) {
                    locale.sendMessage(sender, "no-permission", plugin.getPlaceholders().setPlaceholders(sender));
                    return true;
                }

                plugin.getSettings().STAFFCHAT_MESSAGE.forEach(s -> locale.sendCustomMessage(sender, plugin.getPlaceholders().setPlaceholders(sender, s)));
            } else if (plugin.getSettings().STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    plugin.getMethods().toggleStaffChat(player);
                }
            } else {
                if (!sender.hasPermission(plugin.getPermissions().STAFFCHAT_HELP)) {
                    locale.sendMessage(sender, "no-permission", plugin.getPlaceholders().setPlaceholders(sender));
                    return true;
                }

                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "<g:#8A2387:#E94057:#F27121>SimpleStaffChat &8| &fAvailable Commands:".replace("|", "\u239F"));
                locale.sendCustomMessage(sender, "");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().STAFFCHAT_COMMAND + " <message> &7- Send staffchat messages.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().STAFF_TOGGLE_COMMAND + " &7- Send staffchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().DEVCHAT_COMMAND + " <message> &7- Send adminchat messages.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().DEV_TOGGLE_COMMAND + " &7- Send adminchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().DEVCHAT_COMMAND + " <message> &7- Send devchat messages.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().DEV_TOGGLE_COMMAND + " &7- Send devchat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().CHAT_COMMAND + " <type:staff|admin|dev> &7- Send chat messages without needing to type a command.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().STAFF_HIDE_COMMAND + " <type:staff|admin|dev> &7- Allows you to hide staffchat messages.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().ADMIN_HIDE_COMMAND + " <type:staff|admin|dev> &7- Allows you to hide adminchat messages.");
                locale.sendCustomMessage(sender, "&d/" + plugin.getCommands().DEV_HIDE_COMMAND + " <type:staff|admin|dev> &7- Allows you to hide devchat messages.");
                locale.sendCustomMessage(sender, "&d/staffchatreload &7- Reload the config file.");
            }
        }
        return true;
    }
}