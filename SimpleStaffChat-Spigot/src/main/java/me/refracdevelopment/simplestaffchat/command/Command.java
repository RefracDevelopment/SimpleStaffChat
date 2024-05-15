package me.refracdevelopment.simplestaffchat.command;

import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;
import space.arim.morepaperlib.MorePaperLib;

import java.lang.reflect.Field;
import java.util.*;

public abstract class Command extends org.bukkit.command.Command implements Comparable<Command>, Executable {

    private final String name;
    private final String permission;
    private final Set<String> aliases;
    private String usage;
    private int argsLength;

    public Command(String name, String permission, String... aliases) {
        this(0, "", name, permission, aliases);
    }

    public Command(int argsLength, String usage, String name, String permission, String... aliases) {
        super(name, "", usage, List.of(aliases));

        this.name = name;
        this.permission = permission;
        this.argsLength = argsLength;
        this.usage = usage;

        this.aliases = new HashSet<>();
        this.aliases.add(name);
        Collections.addAll(this.aliases, aliases);

        registerBukkitCommand(aliases);
    }

    private void registerBukkitCommand(String[] aliases) {
        CommandMap commandMap = SimpleStaffChat.getInstance().getMorePaperLib().commandRegistration().getServerCommandMap();

        commandMap.register("simplestaffchat", this);
        for (String alias : aliases) {
            commandMap.register(alias, "simplestaffchat", this);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getPermissionMessage() {
        return SimpleStaffChat.getInstance().getLocaleFile().getString("no-permission");
    }

    @Override
    public String getUsage() {
        return this.usage;
    }

    @Override
    public Command setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public int getArgsLength() {
        return this.argsLength;
    }

    @Override
    public Command setArgsLength(int argsLength) {
        this.argsLength = argsLength;
        return this;
    }

    @Override
    public Set<String> getNameList() {
        return this.aliases;
    }

    @Override
    public boolean hasUsage() {
        return !this.usage.isEmpty();
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}