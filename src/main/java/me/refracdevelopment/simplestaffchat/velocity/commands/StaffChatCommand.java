package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.google.common.base.Joiner;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Config;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Color;
import net.kyori.adventure.text.Component;

public final class StaffChatCommand implements SimpleCommand {

    private final VelocityStaffChat plugin;

    public StaffChatCommand(VelocityStaffChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        if (!Commands.STAFFCHAT_COMMAND_ENABLED.getBoolean()) return;
        CommandSource commandSource = invocation.source();
        
        if (invocation.arguments().length >= 1) {
            String format;
            String message = Joiner.on(" ").join(invocation.arguments());

            format = (commandSource instanceof Player) ? Config.STAFFCHAT_FORMAT.getString().replace("%server%", ((Player) commandSource).getCurrentServer().get().getServerInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_STAFFCHAT_FORMAT.getString().replace("%message%", message);
            
            if (!commandSource.hasPermission(Permissions.STAFFCHAT_COMMAND)) {
                commandSource.sendMessage(Component.text(Config.NO_PERMISSION.getString()));
                return;
            }

            for (Player p : plugin.getServer().getAllPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    Color.sendMessage(p, Color.translate(commandSource, format));
                }
            }
            Color.log2(Color.translate(commandSource, format));
        } else {
            if (Config.STAFFCHAT_OUTPUT.getString().equalsIgnoreCase("custom") && Config.STAFFCHAT_MESSAGE.getStringList() != null) {
                if (!commandSource.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
                    return;
                }

                Config.STAFFCHAT_MESSAGE.getStringList().forEach(s -> {
                    Color.sendMessage(commandSource, s);
                });
            } else if (Config.STAFFCHAT_OUTPUT.getString().equalsIgnoreCase("toggle")) {
                if (commandSource instanceof Player) {
                    Player player = (Player) commandSource;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        Color.sendMessage(player, Config.NO_PERMISSION.getString());
                        return;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_OFF.getString());
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.STAFFCHAT_TOGGLE_ON.getString());
                    }
                }
            } else {
                if (!commandSource.hasPermission(Permissions.STAFFCHAT_HELP)) {
                    Color.sendMessage(commandSource, Config.NO_PERMISSION.getString());
                    return;
                }

                Color.sendMessage(commandSource, "");
                Color.sendMessage(commandSource, "&c&lSimpleStaffChat2 %arrow% Help");
                Color.sendMessage(commandSource, "");
                Color.sendMessage(commandSource, "&c/" + Commands.STAFFCHAT_ALIASES.getStringList().get(0) + " <message> - Send staffchat messages.");
                Color.sendMessage(commandSource, "&c/" + Commands.TOGGLE_ALIASES.getStringList().get(0) + " - Send staffchat messages without needing to type a command.");
                Color.sendMessage(commandSource, "&c/" + Commands.ADMINCHAT_ALIASES.getStringList().get(0) + " <message> - Send adminchat messages.");
                Color.sendMessage(commandSource, "&c/" + Commands.ADMIN_TOGGLE_ALIASES.getStringList().get(0) + " - Send adminchat messages without needing to type a command.");
                Color.sendMessage(commandSource, "&c/" + Commands.DEVCHAT_ALIASES.getStringList().get(0) + " <message> - Send devchat messages.");
                Color.sendMessage(commandSource, "&c/" + Commands.DEV_TOGGLE_ALIASES.getStringList().get(0) + " - Send devchat messages without needing to type a command.");
                Color.sendMessage(commandSource, "&c/" + Commands.RELOAD_ALIASES.getStringList().get(0) + " - Reload the config file.");
                Color.sendMessage(commandSource, "");
            }
        }
    }
}