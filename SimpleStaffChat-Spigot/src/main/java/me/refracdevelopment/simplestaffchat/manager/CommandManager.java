package me.refracdevelopment.simplestaffchat.manager;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.command.Command;
import me.refracdevelopment.simplestaffchat.command.commands.*;
import me.refracdevelopment.simplestaffchat.command.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.command.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.command.commands.devchat.DevChatCommand;
import me.refracdevelopment.simplestaffchat.command.commands.devchat.DevToggleCommand;

import java.util.*;

public class CommandManager {
    private final Set<Command> commands = new HashSet<>();
    private final SimpleStaffChat plugin;

    public CommandManager(SimpleStaffChat plugin) {
        this.plugin = plugin;
    }

    public void registerAll() {
        if (this.plugin.getSettings().STAFFCHAT_ENABLED) {
            this.commands.add(new StaffChatCommand(this.plugin));
        }

        if (this.plugin.getSettings().DEVCHAT_ENABLED) {
            this.commands.add(new DevChatCommand(this.plugin));
        }

        if (this.plugin.getSettings().ADMINCHAT_ENABLED) {
            this.commands.add(new AdminChatCommand(this.plugin));
        }

        if ((this.plugin.getSettings()).CHAT_TOGGLE_ENABLED) {
            this.commands.add(new ToggleCommand(this.plugin));
            this.commands.add(new AdminToggleCommand(this.plugin));
            this.commands.add(new DevToggleCommand(this.plugin));
            this.commands.add(new ChatCommand(this.plugin));
        }

        this.commands.add(new HideCommand(this.plugin));
        this.commands.add(new ReloadCommand(this.plugin));
    }

    public void register(Command... command) {
        commands.addAll(List.of(command));
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