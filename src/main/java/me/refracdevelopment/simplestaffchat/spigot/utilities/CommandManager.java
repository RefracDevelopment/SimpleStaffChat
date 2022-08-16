package me.refracdevelopment.simplestaffchat.spigot.utilities;

import me.refracdevelopment.simplestaffchat.spigot.commands.Command;
import me.refracdevelopment.simplestaffchat.spigot.commands.admin.AdminChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.admin.AdminToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.dev.DevToggleCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.staff.ReloadCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.staff.StaffChatCommand;
import me.refracdevelopment.simplestaffchat.spigot.commands.staff.ToggleCommand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommandManager {

    private static Set<Command> commands = new HashSet<>();

    public static void registerAll(){
        commands.addAll(Arrays.asList(
                new StaffChatCommand(),
                new ToggleCommand(),
                new ReloadCommand(),
                new AdminChatCommand(),
                new AdminToggleCommand(),
                new DevChatCommand(),
                new DevToggleCommand()
        ));
    }

    public static void register(Command... command){
        commands.addAll(Arrays.asList(command));
    }

    public static Optional<Command> byCommand(String command){
        return commands.stream().filter(all -> {
            if(all.getName().equalsIgnoreCase(command)){
                return true;
            }else{
                for(String alias : all.getNameList()){
                    if(alias.equalsIgnoreCase(command)){
                        return true;
                    }
                }
                return false;
            }
        }).findFirst();
    }

}