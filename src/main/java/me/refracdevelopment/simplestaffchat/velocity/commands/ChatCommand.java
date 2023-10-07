package me.refracdevelopment.simplestaffchat.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.refracdevelopment.simplestaffchat.velocity.config.cache.Commands;
import me.refracdevelopment.simplestaffchat.velocity.utilities.Methods;

public class ChatCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        if (!Commands.CHAT_COMMAND_ENABLED.getBoolean()) return;
        if (!(invocation.source() instanceof Player)) return;

        Player player = (Player) invocation.source();

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
