package me.refracdevelopment.simplestaffchat.spigot.manager;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.*;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat.AdminHideCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.devchat.DevHideCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.devchat.DevToggleCommand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private final SimpleStaffChat plugin;
    private final Set<Command> commands = new HashSet<>();

    public CommandManager(SimpleStaffChat plugin) {
        this.plugin = plugin;
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
    }

    public void register(Command... command) {
        commands.addAll(Arrays.asList(command));
    }

    public Optional<Command> byCommand(String command) {
        return commands.stream().filter(all -> {
            if (all.getName().equalsIgnoreCase(command)) {
                return true;
            } else {
                for (String alias : all.getNameList()) {
                    if (alias.equalsIgnoreCase(command)) {
                        return true;
                    }
                }
                return false;
            }
        }).findFirst();
    }

}
