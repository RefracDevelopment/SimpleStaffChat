package me.refracdevelopment.simplestaffchat.spigot.command;

import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Command extends org.bukkit.command.Command implements Comparable<Command>, Executable {

    private SimpleStaffChat plugin;
    private final String name;
    private final String permission;
    private final Set<String> aliases;
    private String usage;
    private int argsLength;

    public Command(SimpleStaffChat plugin, String name, String permission, String... aliases) {
        this(plugin, 0, "", name, permission, aliases);
    }

    public Command(SimpleStaffChat plugin, int argsLength, String usage, String name, String permission, String... aliases) {
        super(name, "", usage, Arrays.asList(aliases));

        this.plugin = plugin;
        this.name = name;
        this.permission = permission;
        this.argsLength = argsLength;
        this.usage = Color.translate(usage);

        this.aliases = new HashSet<>();
        this.aliases.add(name);
        Collections.addAll(this.aliases, aliases);

        registerBukkitCommand(aliases);
    }

    private void registerBukkitCommand(String[] aliases) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("command", this);
            for (String alias : aliases) {
                commandMap.register(alias, "command", this);
            }
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            Color.log("&cCould not register a command properly (name: " + this.name + "), stacktrace: " + exception.getMessage());
            exception.printStackTrace();
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
        return Color.translate(plugin.getLocaleFile().getString("no-permission"));
    }

    @Override
    public String getUsage() {
        return this.usage;
    }

    @Override
    public int getArgsLength() {
        return this.argsLength;
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
    public Command setArgsLength(int argsLength) {
        this.argsLength = argsLength;
        return this;
    }

    @Override
    public Command setUsage(String usage) {
        this.usage = Color.translate(usage);
        return this;
    }

    @Override
    public int compareTo(@NotNull Command o) {
        return 0;
    }
}
