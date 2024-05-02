package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Modified version of <a href="https://github.com/RyanMoodGAMING/RyUtils">RyMessageUtils</a>
 */
@UtilityClass
@SuppressWarnings("unused")
public class RyMessageUtils {

    private final Object instance = SimpleStaffChat.getInstance();
    private final ProxyServer server = SimpleStaffChat.getInstance().getServer();
    private final String pluginId = "simplestaffchat";

    @Getter
    private final String prefix = SimpleStaffChat.getInstance().getLocaleFile().getString("prefix");
    @Getter
    private final String errorPrefix = "&cError &7» &c";
    @Getter
    private final String breaker = "&7&m------------------------------------";
    @Getter
    private final String supportMessage = "Please contact the plugin author for support.";

    /**
     * Translates the message given and for colours, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player%)
     * @param message The message you wish to be translated.
     * @return a translated String
     */
    public Component translate(Player player, String message) {
        message = message.replace("%player%", player.getUsername())
                .replace("%prefix%", getPrefix());

        return translate(message);
    }

    /**
     * Translates the message given for colours and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return a translated String
     */
    public Component translate(String message) {
        message = message
                .replace("%prefix%", getPrefix())
                .replaceAll("&1", "<dark_blue>")
                .replaceAll("&2", "<dark_green>")
                .replaceAll("&3", "<dark_aqua>")
                .replaceAll("&4", "<dark_red>")
                .replaceAll("&5", "<dark_purple>")
                .replaceAll("&6", "<gold>")
                .replaceAll("&7", "<gray>")
                .replaceAll("&8", "<dark_gray>")
                .replaceAll("&9", "<blue>")
                .replaceAll("&a", "<green>")
                .replaceAll("&b", "<aqua>")
                .replaceAll("&c", "<red>")
                .replaceAll("&d", "<light_purple>")
                .replaceAll("&e", "<yellow>")
                .replaceAll("&f", "<white>")
                .replaceAll("&l", "<bold>")
                .replaceAll("&k", "<obfuscated>")
                .replaceAll("&m", "<strikethrough>")
                .replaceAll("&n", "<underlined>")
                .replaceAll("&o", "<italic>")
                .replaceAll("&r", "<reset>");

        Component component = MiniMessage.miniMessage().deserialize(message);

        return component;
    }

    /**
     * Translates the string list for colours and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return a string list of translated messages.
     */
    public List<Component> translate(@NotNull List<String> messages) {
        return messages.stream().map(RyMessageUtils::translate).collect(Collectors.toList());
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public void sendPlayer(@NotNull Player player, @NotNull String message) {
        player.sendMessage(translate(player, message));
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public void sendPlayer(@NotNull Player player, @NotNull String... messages) {
        for (String message : messages) {
            player.sendMessage(translate(player, message));
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public void sendPlayer(@NotNull Player player, @NotNull List<String> messages) {
        for (String message : messages) {
            player.sendMessage(translate(message));
        }
    }

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public void sendSender(@NotNull CommandSource sender, @NotNull String message) {
        sender.sendMessage(translate(message));
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public void sendSender(@NotNull CommandSource sender, @NotNull String... messages) {
        for (String message : messages) {
            sender.sendMessage(translate(message));
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public void sendSender(@NotNull CommandSource sender, @NotNull List<String> messages) {
        for (String message : messages) {
            sender.sendMessage(translate(message));
        }
    }

    /**
     * Send console a message.
     *
     * @param prefix  If you would like the plugin prefix to be added at the beginning of the message.
     * @param message The message you wish for console to receive.
     */
    public void sendConsole(boolean prefix, String message) {
        if (prefix) {
            server.getConsoleCommandSource().sendMessage(translate(getPrefix() + " " + message));
        } else {
            server.getConsoleCommandSource().sendMessage(translate(message));
        }
    }

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    public void sendConsole(boolean prefix, String... messages) {
        if (prefix) {
            for (String message : messages) {
                server.getConsoleCommandSource().sendMessage(translate(getPrefix() + message));
            }
        } else {
            for (String message : messages) {
                server.getConsoleCommandSource().sendMessage(translate(message));
            }
        }
    }

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    public void sendConsole(boolean prefix, List<String> messages) {
        if (prefix) {
            for (String message : messages) {
                server.getConsoleCommandSource().sendMessage(translate(getPrefix() + message));
            }
        } else {
            for (String message : messages) {
                server.getConsoleCommandSource().sendMessage(translate(message));
            }
        }
    }

    /**
     * Send a permission based broadcast to all online players.
     *
     * @param player     The player who is making the broadcast.
     * @param permission The permission the players require to see the broadcast.
     * @param message    The message you wish to be broadcast.
     */
    public void broadcast(@Nullable Player player, String permission, String message) {
        for (Player online : server.getAllPlayers()) {
            if (online.hasPermission(permission)) {
                online.sendMessage(translate(message));
            }
        }
    }

    /**
     * Send a broadcast to all online players.
     *
     * @param message The message you wish to be sent to the players.
     */
    public void broadcast(String message) {
        for (Player online : server.getAllPlayers()) {
            online.sendMessage(translate(message));
        }
    }

    /**
     * Send a broadcast to all online players.
     *
     * @param player  The player who is sending the broadcast.
     * @param message The message you wish to be sent to players.
     */
    public void broadcast(Player player, String message) {
        for (Player online : server.getAllPlayers()) {
            online.sendMessage(translate(player, message));
        }
    }

    /**
     * Sends a message to console saying that the license has been authenticated.
     */
    public void sendLicenseSucessful() {
        sendConsole(true, breaker,
                "&fLicense has been authenticated. ",
                breaker);
    }

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error The error that occurred.
     */
    public void sendLicenseError(String error) {
        sendConsole(true, breaker,
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
    }

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error         The error that occurred.
     * @param disablePlugin Should the plugin be disabled?
     */
    public void sendLicenseError(String error, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (disablePlugin && instance != null && server != null) {
            server.getPluginManager().getPlugin(pluginId).get().getExecutorService().shutdown();
        }
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error The error that occurred.
     */
    public void sendPluginError(String error) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error         The error that has occurred.
     * @param disablePlugin Should the plugin be disabled due to the error?
     */
    public void sendPluginError(String error, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (disablePlugin && instance != null && server != null) {
            server.getPluginManager().getPlugin(pluginId).get().getExecutorService().shutdown();
        }
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error     The error that occurred.
     * @param exception The exception that occurred.
     * @param debug     Is debug enabled?
     */
    public void sendPluginError(String error, Exception exception, boolean debug) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, "&fAs you have debug enabled in your config.yml, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
    }

    /**
     * Sends a message to console saying an that there was an error.
     *
     * @param error         The error that occurred.
     * @param exception     The exception that occurred.
     * @param debug         Is debug enabled?
     * @param disablePlugin Should the plugin be disabled due to the error?
     */
    public void sendPluginError(String error, Exception exception, boolean debug, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, "&fAs you have debug enabled in your config.yml, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
        if (disablePlugin && instance != null && server != null) {
            server.getPluginManager().getPlugin(pluginId).get().getExecutorService().shutdown();
        }
    }

}