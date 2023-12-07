package me.refracdevelopment.simplestaffchat.bungee.manager;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.commands.*;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminHideCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevHideCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevToggleCommand;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private final BungeeStaffChat plugin;
    private final Set<Command> commands = new HashSet<>();

    public CommandManager(BungeeStaffChat plugin) {
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

        if (plugin.getCommands().STAFF_HIDE_COMMAND_ENABLED) {
            commands.add(new HideCommand(plugin));
        }

        if (plugin.getCommands().DEVCHAT_COMMAND_ENABLED) {
            commands.add(new DevChatCommand(plugin));
        }

        if (plugin.getCommands().DEV_HIDE_COMMAND_ENABLED) {
            commands.add(new DevHideCommand(plugin));
        }

        if (plugin.getCommands().DEV_TOGGLE_COMMAND_ENABLED) {
            commands.add(new DevToggleCommand(plugin));
        }

        if (plugin.getCommands().ADMINCHAT_COMMAND_ENABLED) {
            commands.add(new AdminChatCommand(plugin));
        }

        if (plugin.getCommands().ADMIN_HIDE_COMMAND_ENABLED) {
            commands.add(new AdminHideCommand(plugin));
        }

        if (plugin.getCommands().ADMIN_TOGGLE_COMMAND_ENABLED) {
            commands.add(new AdminToggleCommand(plugin));
        }

        if (plugin.getCommands().CHAT_COMMAND_ENABLED) {
            commands.add(new ChatCommand(plugin));
        }

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