package me.refracdevelopment.simplestaffchat.bungee.manager;

import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.commands.ChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.ReloadCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.ToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.adminchat.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.bungee.commands.devchat.DevChatCommand;
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
        commands.addAll(Arrays.asList(
                new StaffChatCommand(),
                new ToggleCommand(),
                new ReloadCommand(plugin),
                new AdminChatCommand(),
                new AdminToggleCommand(),
                new DevChatCommand(),
                new DevToggleCommand(),
                new ChatCommand()
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