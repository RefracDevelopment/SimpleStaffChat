package me.refracdevelopment.simplestaffchat.spigot.command;

import org.bukkit.command.CommandSender;

import java.util.Set;

public interface Executable {

    /**
     * @return The name of the command.
     */
    String getName();

    /**
     * @return The usage (syntax) of the command.
     */
    String getUsage();

    /**
     * @return The required arguments length.
     */
    int getArgsLength();

    /**
     * @return A set with the alliases of the command (incl. Executable#getName).
     */
    Set<String> getNameList();

    /**
     * @return Boolean; is the usage set.
     */
    boolean hasUsage();

    /**
     * Re-sets the argsLength variable.
     *
     * @param argsLength The new amount of arguments required for the command.
     * @return The command class.
     */
    Command setArgsLength(int argsLength);

    /**
     * Change the command's usage (syntax).
     *
     * @param usage The new usage variable.
     * @return The command class.
     */
    Command setUsage(String usage);

    boolean execute(CommandSender sender, String command, String[] args);
}