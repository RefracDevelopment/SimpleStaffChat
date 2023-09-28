package me.refracdevelopment.simplestaffchat.spigot.manager;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.commands.ChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.devchat.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.utilities.command.Command;

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
                new ChatCommand(plugin)
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
