package me.refracdevelopment.simplestaffchat.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import me.refracdevelopment.simplestaffchat.utilities.chat.Color;

public class ChatCommand implements SimpleCommand {

    private final SimpleStaffChat plugin;

    public ChatCommand(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player player)) {
            Color.sendConfigMessage(invocation.source(), "no-console");
            return;
        }

        if (!player.hasPermission(plugin.getCommands().CHAT_COMMAND_PERMISSION)) {
            Color.sendConfigMessage(player, "no-permission");
            return;
        }

        if (invocation.arguments().length == 0) {
            Color.sendMessage(player, "&c/" + plugin.getCommands().CHAT_COMMAND_ALIASESES.get(0) + " <staff|admin|dev|all>");
            return;
        }

        switch (invocation.arguments()[0]) {
            case "staff":
                Methods.toggleStaffChat(player);
                break;
            case "admin":
                Methods.toggleAdminChat(player);
                break;
            case "dev":
                Methods.toggleDevChat(player);
                break;
            case "all":
                Methods.toggleAllChat(player);
                break;
        }
    }
}
