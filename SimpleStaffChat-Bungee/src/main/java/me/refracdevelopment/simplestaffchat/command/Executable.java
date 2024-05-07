package me.refracdevelopment.simplestaffchat.command;

import net.md_5.bungee.api.CommandSender;

import java.util.Set;

public interface Executable {
    String getName();

    String getUsage();

    int getArgsLength();

    Set<String> getNameList();

    boolean hasUsage();

    Command setArgsLength(int paramInt);

    Command setUsage(String paramString);

    void execute(CommandSender paramCommandSender, String[] paramArrayOfString);
}