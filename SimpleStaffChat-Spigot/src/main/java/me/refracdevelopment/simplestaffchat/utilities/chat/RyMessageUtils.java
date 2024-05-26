package me.refracdevelopment.simplestaffchat.utilities.chat;

import com.google.common.collect.ImmutableMap;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.Methods;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Modified version of <a href="https://github.com/RyanMoodGAMING/RyUtils">RyMessageUtils</a>
 */
@SuppressWarnings("unused")
public class RyMessageUtils {

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
    @Setter
    private static SimpleStaffChat instance;
    @Setter
    @Getter
    private static BukkitAudiences audiences;
    @Getter
    @Setter
    private static String prefix;
    @Getter
    @Setter
    private static String errorPrefix = "&cError &7» &c";
    @Getter
    @Setter
    private static String breaker = "&7&m------------------------------------";
    @Getter
    @Setter
    private static String supportMessage = "Please contact the plugin author for support.";

    static {
        instance = SimpleStaffChat.getInstance();

        // If this check is not present it will throw an error when trying to use MiniMessage
        if (PaperLib.isSpigot())
            audiences = BukkitAudiences.create(instance);
        else
            audiences = null;

        prefix = instance.getLocaleFile().getString("prefix");
    }

    /**
     * Translates the message given and for colours using AdventureAPI, PAPI, %prefix% and %player%.
     *
     * @param player  The player that is being translated (%player% and PAPI)
     * @param message The message you wish to be translated.
     * @return a translated Component
     */
    public static Component translate(Player player, String message) {
        message = Placeholders.setPlaceholders(player, message);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
             message = PlaceholderAPI.setPlaceholders(player, message);

            return translate(message);
        }

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

        Component component = MiniMessage.miniMessage().deserialize(message);

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
     * Translates the string list for colours using AdventureAPI and %prefix%.
     *
     * @param messages The string list you wish to be translated.
     * @return a component list of translated messages.
     */
    public static List<Component> translate(@NotNull List<String> messages) {
        return messages.stream().map(RyMessageUtils::translate).collect(Collectors.toList());
    }

    /**
     * Send a player a message.
     *
     * @param player  The player who you wish to receive the message.
     * @param message The message you wish to send the player.
     */
    public static void sendPlayer(@NotNull Player player, @NotNull String message) {
        if (getAudiences() != null) {
            getAudiences().player(player).sendMessage(translate(player, message));
        } else {
            player.sendMessage(translate(player, message));
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public static void sendPlayer(@NotNull Player player, @NotNull String... messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().player(player).sendMessage(translate(player, message));
            } else {
                player.sendMessage(translate(player, message));
            }
        }
    }

    /**
     * Send a player multiple messages at once.
     *
     * @param player   The player who you wish to receive the messages.
     * @param messages The string list of messages you wish to send to the player.
     */
    public static void sendPlayer(Player player, @NotNull List<String> messages) {
        for (String message : messages) {
            if (getAudiences() != null) {
                getAudiences().player(player).sendMessage(translate(player, message));
            } else {
                player.sendMessage(translate(player, message));
            }
        }
    }

    /**
     * Send a sender a message.
     *
     * @param sender  The sender who you wish to receive the messages.
     * @param message The message you wish to send to the sender.
     */
    public static void sendSender(@NotNull CommandSender sender, @NotNull String message) {
        message = Placeholders.setPlaceholders(sender, message);

        if (getAudiences() != null) {
            getAudiences().sender(sender).sendMessage(translate(message));
        } else {
            sender.sendMessage(translate(message));
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public static void sendSender(@NotNull CommandSender sender, @NotNull String... messages) {
        for (String message : messages) {
            message = Placeholders.setPlaceholders(sender, message);

            if (getAudiences() != null) {
                getAudiences().sender(sender).sendMessage(translate(message));
            } else {
                sender.sendMessage(translate(message));
            }
        }
    }

    /**
     * Send a sender multiple messages
     *
     * @param sender   The sender who you wish to receive the messages.
     * @param messages The messages you wish to send to the sender.
     */
    public static void sendSender(@NotNull CommandSender sender, @NotNull List<String> messages) {
        for (String message : messages) {
            message = Placeholders.setPlaceholders(sender, message);

            if (getAudiences() != null) {
                getAudiences().sender(sender).sendMessage(translate(message));
            } else {
                sender.sendMessage(translate(message));
            }
        }
    }

    /**
     * Send console a message.
     *
     * @param prefix  If you would like the plugin prefix to be added at the beginning of the message.
     * @param message The message you wish for console to receive.
     */
    public static void sendConsole(boolean prefix, String message) {
        if (prefix) message = "<#7D0DC3>[SimpleStaffChat] &f" + message;

        if (getAudiences() != null) {
            getAudiences().console().sendMessage(translate(message));
        } else {
            Bukkit.getConsoleSender().sendMessage(translate(message));
        }
    }

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    public static void sendConsole(boolean prefix, String... messages) {
        for (String message : messages) {
            if (prefix) message = "<#7D0DC3>[SimpleStaffChat] &f" + message;

            if (getAudiences() != null) {
                getAudiences().console().sendMessage(translate(message));
            } else {
                Bukkit.getConsoleSender().sendMessage(translate(message));
            }
        }
    }

    /**
     * Send console multiple messages.
     *
     * @param prefix   If you would like the plugin prefix to be added at the beginning of the message.
     * @param messages The messages you wish to send to console.
     */
    public static void sendConsole(boolean prefix, List<String> messages) {
        for (String message : messages) {
            if (prefix) message = "<#7D0DC3>[SimpleStaffChat] &f" + message;

            if (getAudiences() != null) {
                getAudiences().console().sendMessage(translate(message));
            } else {
                Bukkit.getConsoleSender().sendMessage(translate(message));
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
    public static void sendStaffChat(Player player, String permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission) && !Methods.getStaffChatMuted().contains(online.getUniqueId())) {
                if (getAudiences() != null) {
                    getAudiences().player(online).sendMessage(translate(player, message));
                } else {
                    online.sendMessage(translate(player, message));
                }
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
    public static void sendDevChat(Player player, String permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission) && !Methods.getDevChatMuted().contains(online.getUniqueId())) {
                if (getAudiences() != null) {
                    getAudiences().player(online).sendMessage(translate(player, message));
                } else {
                    online.sendMessage(translate(player, message));
                }
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
    public static void sendAdminChat(Player player, String permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission) && !Methods.getAdminChatMuted().contains(online.getUniqueId())) {
                if (getAudiences() != null) {
                    getAudiences().player(online).sendMessage(translate(player, message));
                } else {
                    online.sendMessage(translate(player, message));
                }
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
    public static void broadcast(Player player, Permission permission, String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(permission)) {
                if (getAudiences() != null) {
                    getAudiences().player(online).sendMessage(translate(player, message));
                } else {
                    online.sendMessage(translate(player, message));
                }
            }
        }
    }

    /**
     * Send a broadcast to all online players.
     *
     * @param message The message you wish to be sent to the players.
     */
    public static void broadcast(String message) {
        if (getAudiences() != null) {
            getAudiences().players().sendMessage(translate(message));
        } else {
            Bukkit.getConsoleSender().sendMessage(translate(message));
        }
    }

    /**
     * Send a broadcast to all online players.
     *
     * @param player  The player who is sending the broadcast.
     * @param message The message you wish to be sent to players.
     */
    public static void broadcast(Player player, String message) {
        if (getAudiences() != null) {
            getAudiences().players().sendMessage(translate(player, message));
        } else {
            Bukkit.getConsoleSender().sendMessage(translate(player, message));
        }
    }

    /**
     * Sends a message to console saying that the license has been authenticated.
     */
    public static void sendLicenseSucessful() {
        sendConsole(true, breaker,
                "&fLicense has been authenticated. ",
                breaker);
    }

    /**
     * Sends a message to console saying that there was a license error.
     *
     * @param error The error that occurred.
     */
    public static void sendLicenseError(String error) {
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
    public static void sendLicenseError(String error, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error occurred while verifying your license.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (disablePlugin && instance != null) {
            Bukkit.getPluginManager().disablePlugin(instance);
        }
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error The error that occurred.
     */
    public static void sendPluginError(String error) {
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
    public static void sendPluginError(String error, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (disablePlugin && instance != null) {
            Bukkit.getPluginManager().disablePlugin(instance);
        }
    }

    /**
     * Sends a message to console saying that there was an error.
     *
     * @param error     The error that occurred.
     * @param exception The exception that occurred.
     * @param debug     Is debug enabled?
     */
    public static void sendPluginError(String error, Exception exception, boolean debug) {
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
    public static void sendPluginError(String error, Exception exception, boolean debug, boolean disablePlugin) {
        sendConsole(true, breaker,
                "&fAn error has occurred.",
                "&fError: &c" + error,
                getSupportMessage(),
                breaker);
        if (debug) {
            sendConsole(true, "&fAs you have debug enabled in your config.yml, the following stacktrace error is due to this:");
            exception.printStackTrace();
        }
        if (disablePlugin && instance != null) {
            Bukkit.getPluginManager().disablePlugin(instance);
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
    public static void sendPluginMessage(CommandSender sender, String message) {
        sendSender(sender, instance.getLocaleFile().getString(message));
    }

}