package me.refracdevelopment.simplestaffchat.bungee.manager;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.commands.*;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminHideCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevHideCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.utilities.Manager;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager extends Manager {

    private final Set<Command> commands = new HashSet<>();

    public CommandManager(BungeeStaffChat plugin) {
        super(plugin);
    }

    public void registerAll() {
        commands.addAll(Arrays.asList(
                new StaffChatCommand(plugin),
                new ToggleCommand(plugin),
                new ReloadCommand(plugin),
                new AdminChatCommand(plugin),
                new AdminToggleCommand(plugin),
                new DevChatCommand(plugin),
                new DevToggleCommand(plugin),
                new ChatCommand(plugin),
                new HideCommand(plugin),
                new AdminHideCommand(plugin),
                new DevHideCommand(plugin)
        ));
        commands.forEach(command -> plugin.getProxy().getPluginManager().registerCommand(plugin, command));
    }

    public void register(Command... command) {
        commands.addAll(Arrays.asList(command));
        commands.forEach(commands -> plugin.getProxy().getPluginManager().registerCommand(plugin, commands));
    }

    public Optional<Command> byCommand(String command) {
        return commands.stream().filter(all -> {
            if (all.getName().equalsIgnoreCase(command)) {
                return true;
            } else {
                for (String alias : all.getAliases()) {
                    if (alias.equalsIgnoreCase(command)) {
                        return true;
                    }
                }
                return false;
            }
        }).findFirst();
    }

}