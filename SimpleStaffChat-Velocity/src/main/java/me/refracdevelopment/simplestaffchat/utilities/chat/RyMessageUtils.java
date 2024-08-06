package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.google.common.collect.ImmutableMap;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Modified version of <a href="https://github.com/RyanMoodGAMING/RyUtils">RyMessageUtils</a>
 */
@UtilityClass
@SuppressWarnings("unused")
public class RyMessageUtils {

    private final SimpleStaffChat instance;
    private final ProxyServer server;
    private final String pluginId = "simplestaffchat";

    @Getter
    private final String prefix;
    @Getter
    private final String errorPrefix = "&cError &7» &c";
    @Getter
    private final String breaker = "&7&m------------------------------------";
    @Getter
    private final String supportMessage = "Please contact the plugin author for support.";

    private static final Pattern HEX_PATTERN = Pattern.compile("(?i)&#([0-9A-F]{6})");
    private static final Pattern AMPERSAND_PATTERN = Pattern.compile("(?i)&([0-9A-FK-ORX#])");
    private static final Map<String, String> codeTranslations = new ImmutableMap.Builder<String, String>()
            .put("0", "<black>")
            .put("1", "<dark_blue>")
            .put("2", "<dark_green>")
            .put("3", "<dark_aqua>")
            .put("4", "<dark_red>")
            .put("5", "<dark_purple>")
            .put("6", "<gold>")
            .put("7", "<gray>")
            .put("8", "<dark_gray>")
            .put("9", "<blue>")
            .put("a", "<green>")
            .put("b", "<aqua>")
            .put("c", "<red>")
            .put("d", "<light_purple>")
            .put("e", "<yellow>")
            .put("f", "<white>")
            .put("k", "<obfuscated>")
            .put("l", "<bold>")
            .put("m", "<strikethrough>")
            .put("n", "<underlined>")
            .put("o", "<italic>")
            .put("r", "<reset>")
            .build();

    static {
        instance = SimpleStaffChat.getInstance();
        server = instance.getServer();
        prefix = instance.getLocaleFile().getString("prefix");
    }

    /**
     * Translates the message given and for colours, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player%)
     * @param message The message you wish to be translated.
     * @return a translated String
     */
    public Component translate(Player player, String message) {
        message = Placeholders.setPlaceholders(player, message);

        return translate(message);
    }

    /**
     * Translates the message given for colours using AdventureAPI and %prefix%.
     *
     * @param message The message you wish to be translated.
     * @return a translated Component
     */
    public static Component translate(String message) {
        message = legacyToAdventure(message);

        Component component = MiniMessage.miniMessage().deserialize(message)
                .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                .decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.FALSE)
                .decorationIfAbsent(TextDecoration.OBFUSCATED, TextDecoration.State.FALSE);

        return component;
    }

    /**
     * Translate a string from legacy to Adventure API.
     *
     * @param input The string that needs translating.
     * @return      String which is in an adventure format.
     *
     * @Author: EternalCodeTeam (https://github.com/EternalCodeTeam/ChatFormatter/)
     */
    private static String legacyToAdventure(String input) {
        String result = HEX_PATTERN.matcher(input).replaceAll(matchResult -> {
            String hex = matchResult.group(1);
            return "<#" + hex + ">";
        });

        result = AMPERSAND_PATTERN.matcher(result).replaceAll(matchResult -> {
            String color = matchResult.group(1);
            String adventure = codeTranslations.get(color.toLowerCase());

            if (adventure == null) {
                return matchResult.group();
            }

            return adventure;
        });

        return result;
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
        message = Placeholders.setPlaceholders(sender, message);

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
            message = Placeholders.setPlaceholders(sender, message);

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
            message = Placeholders.setPlaceholders(sender, message);

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
            server.getConsoleCommandSource().sendMessage(translate("<#7D0DC3>[" + instance.getContainer().getDescription().getName() + "] &f" + message));
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
                server.getConsoleCommandSource().sendMessage(translate("<#7D0DC3>[SimpleStaffChat] &f" + message));
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
                server.getConsoleCommandSource().sendMessage(translate("<#7D0DC3>[SimpleStaffChat] &f" + message));
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
        message = Placeholders.setPlaceholders(player, message);

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
        message = Placeholders.setPlaceholders(player, message);

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

    /**
     * Send a player a message from the locale/messages file.
     *
     * @param player  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public static void sendPluginMessage(Player player, String message) {
        sendPlayer(player, instance.getLocaleFile().getString(message));
    }

    /**
     * Send a sender a message from the locale/messages file.
     *
     * @param sender  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public static void sendPluginMessage(CommandSource sender, String message) {
        sendSender(sender, instance.getLocaleFile().getString(message));
    }

}