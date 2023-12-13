package me.refracdevelopment.simplestaffchat.spigot.manager;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.command.Command;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.*;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.command.commands.devchat.DevChatCommand;
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
        if (plugin.getCommands().STAFFCHAT_COMMAND_ENABLED) {
            commands.add(new StaffChatCommand(plugin));
        }

        if (plugin.getCommands().STAFF_TOGGLE_COMMAND_ENABLED) {
            commands.add(new ToggleCommand(plugin));
        }

        commands.add(new ReloadCommand(plugin));

        if (plugin.getCommands().HIDE_COMMAND_ENABLED) {
            commands.add(new HideCommand(plugin));
        }

        if (plugin.getCommands().DEVCHAT_COMMAND_ENABLED) {
            commands.add(new DevChatCommand(plugin));
        }

        if (plugin.getCommands().DEV_TOGGLE_COMMAND_ENABLED) {
            commands.add(new DevToggleCommand(plugin));
        }

        if (plugin.getCommands().ADMINCHAT_COMMAND_ENABLED) {
            commands.add(new AdminChatCommand(plugin));
        }

        if (plugin.getCommands().ADMIN_TOGGLE_COMMAND_ENABLED) {
            commands.add(new AdminToggleCommand(plugin));
        }

        if (plugin.getCommands().CHAT_COMMAND_ENABLED) {
            commands.add(new ChatCommand(plugin));
        }
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
