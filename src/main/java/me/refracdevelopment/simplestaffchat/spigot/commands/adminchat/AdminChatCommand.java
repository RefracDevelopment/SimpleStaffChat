package me.refracdevelopment.simplestaffchat.spigot.commands.adminchat;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.config.Commands;
import me.refracdevelopment.simplestaffchat.spigot.config.Config;
import me.refracdevelopment.simplestaffchat.spigot.manager.LocaleManager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Color;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminChatCommand extends Command {

    private final SimpleStaffChat plugin;

    public AdminChatCommand(SimpleStaffChat plugin) {
        super(Commands.ADMINCHAT_COMMAND, Permissions.ADMINCHAT_COMMAND, Commands.ADMINCHAT_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!Commands.ADMINCHAT_COMMAND_ENABLED) return false;

        final LocaleManager locale = plugin.getManager(LocaleManager.class);

        String message = Joiner.on(" ").join(args);

        if (args.length >= 1) {
            String format = (sender instanceof Player) ? Config.ADMINCHAT_FORMAT.replace("%message%", message) :
                    Config.CONSOLE_ADMINCHAT_FORMAT.replace("%message%", message);

            if (!sender.hasPermission(Permissions.ADMINCHAT_COMMAND)) {
                locale.sendMessage(sender, "no-permission");
                return true;
            }

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.ADMINCHAT_SEE)) {
                    locale.sendCustomMessage(p, Color.translate(sender, format));
                }
            }
            if (Config.BUNGEECORD && sender instanceof Player) {
                Player player = (Player) sender;
                plugin.getPluginMessage().sendStaffChat(player, Color.translate(sender, format));
            }
            Color.log2(Color.translate(sender, format));
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}