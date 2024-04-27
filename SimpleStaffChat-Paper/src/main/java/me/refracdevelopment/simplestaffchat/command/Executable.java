package me.refracdevelopment.simplestaffchat.command;

import org.bukkit.command.CommandSender;

import java.util.Set;

public interface Executable {
    String getName();

    String getUsage();

    int getArgsLength();

    Set<String> getNameList();

    boolean hasUsage();

    Command setArgsLength(int paramInt);

    Command setUsage(String paramString);

    boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
}