package me.refracdevelopment.simplestaffchat.bungee.config;

import lombok.Getter;
import lombok.Setter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;

import java.util.Arrays;
import java.util.List;

/*
 * Taken from
 * https://github.com/BGHDDevelopment/PunishmentGUIRecode/blob/master/src/main/java/net/bghddevelopment/punishmentgui/language/Language.java
 * Modified for my needs
 */
public enum Config {

    STAFFCHAT_SYMBOL("staffchat-symbol", "@"),
    ADMINCHAT_SYMBOL("adminchat-symbol", "#"),
    DEVCHAT_SYMBOL("devchat-symbol", "%"),
    LUCKPERMS("luckperms", false),
    FORMAT_ADMINCHAT("format.adminchat-format", "&8[&c&lAdminChat&8] &e(%server%) &c&l%player%&7: &f%message%"),
    FORMAT_DEVCHAT("format.devchat-format", "&8[&d&lDevChat&8] &e(%server%) &b&l%player%&7: &f%message%"),
    FORMAT_STAFFCHAT("format.minecraft-format", "%prefix% &e(%server%) &6&l%player%&7: &f%message%"),
    CONSOLE_STAFFCHAT("format.console-staffchat-format", "%prefix% &6&lConsole&7: &f%message%"),
    CONSOLE_ADMINCHAT("format.console-adminchat-format", "&8[&c&lAdminChat&8] &c&lConsole&7: &f%message%"),
    CONSOLE_DEVCHAT("format.console-devchat-format", "&8[&d&lDevChat&8] &e&lConsole&7: &f%message%"),
    JOIN_ENABLED("join.enabled", false),
    JOIN_FORMAT("join.join-format", "&8[&a+&8] &9%player% &fjoined &e%server%&f."),
    SWITCH_FORMAT("join.switch-format", "&8[&b+&8] &9%player% &fswitched server &7(&e%from% &f%arrow% &e%server%&7)&f."),
    QUIT_FORMAT("join.quit-format", "&8[&c-&8] &9%player% &fleft &e%server%&f."),
    MESSAGES_PREFIX("messages.prefix", "&8[&4&lStaffChat&8]"),
    MESSAGES_NO_PERMISSION("messages.no-permission", "&cYou don't have permission to execute this command."),
    MESSAGES_RELOAD("messages.reload", "&7Config files reloaded. Changes should be live in-game."),
    MESSAGES_TOGGLE_ON("messages.toggle-on", "%prefix% &7StaffChat toggled &aon&7."),
    MESSAGES_TOGGLE_OFF("messages.toggle-off", "%prefix% &7StaffChat toggled &coff&7."),
    MESSAGES_ADMINCHAT_TOGGLE_ON("messages.adminchat-toggle-on", "&8[&c&lAdminChat&8] &7AdminChat toggled &aon&7."),
    MESSAGES_ADMINCHAT_TOGGLE_OFF("messages.adminchat-toggle-off", "&8[&c&lAdminChat&8] &7AdminChat toggled &coff&7."),
    MESSAGES_DEVCHAT_TOGGLE_ON("messages.devchat-toggle-on", "&8[&d&lDevChat&8] &7DevChat toggled &aon&7."),
    MESSAGES_DEVCHAT_TOGGLE_OFF("messages.devchat-toggle-off", "&8[&d&lDevChat&8] &7DevChat toggled &coff&7."),
    MESSAGES_STAFFCHAT_OUTPUT("messages.staffchat-output", "default"),
    MESSAGES_STAFFCHAT_MESSAGE("messages.staffchat-message", Arrays.asList(
            "",
            "&c&lSimpleStaffChat2 %arrow2% Help",
            "",
            "&c/staffchat <message> - Send staffchat messages.",
            "&c/adminchat <message> - Send adminchat messages.",
            "&c/devchat <message> - Send devchat messages.",
            "&c/staffchattoggle - Send staffchat messages without needing to type a command.",
            "&c/adminchattoggle - Send adminchat messages without needing to type a command.",
            "&c/devchattoggle - Send devchat messages without needing to type a command.",
            "&c/staffchatreload - Reload the config file.",
            ""
    )),
    COMMANDS_STAFFCHAT_ENABLED("commands.staffchat.enabled", true),
    COMMANDS_STAFFCHAT_COMMAND("commands.staffchat.command", "staffchat"),
    COMMANDS_STAFFCHAT_ALIASES("commands.staffchat.alias", Arrays.asList("sc")),
    COMMANDS_ADMINCHAT_ENABLED("commands.adminchat.enabled", true),
    COMMANDS_ADMINCHAT_COMMAND("commands.adminchat.command", "adminchat"),
    COMMANDS_ADMINCHAT_ALIASES("commands.adminchat.alias", Arrays.asList("ac")),
    COMMANDS_DEVCHAT_ENABLED("commands.devchat.enabled", true),
    COMMANDS_DEVCHAT_COMMAND("commands.devchat.command", "devchat"),
    COMMANDS_DEVCHAT_ALIASES("commands.devchat.alias", Arrays.asList("dc")),
    COMMANDS_TOGGLE_ENABLED("commands.toggle.enabled", true),
    COMMANDS_TOGGLE_COMMAND("commands.toggle.command", "staffchattoggle"),
    COMMANDS_TOGGLE_ALIASES("commands.toggle.alias", Arrays.asList("sctoggle", "sct")),
    COMMANDS_ADMINCHAT_TOGGLE_ENABLED("commands.adminchat-toggle.enabled", true),
    COMMANDS_ADMINCHAT_TOGGLE_COMMAND("commands.adminchat-toggle.command", "adminchattoggle"),
    COMMANDS_ADMINCHAT_TOGGLE_ALIASES("commands.adminchat-toggle.alias", Arrays.asList("actoggle", "act")),
    COMMANDS_DEVCHAT_TOGGLE_ENABLED("commands.devchat-toggle.enabled", true),
    COMMANDS_DEVCHAT_TOGGLE_COMMAND("commands.devchat-toggle.command", "devchattoggle"),
    COMMANDS_DEVCHAT_TOGGLE_ALIASES("commands.devchat-toggle.alias", Arrays.asList("dctoggle", "dct")),
    COMMANDS_RELOAD_ENABLED("commands.reload.enabled", true),
    COMMANDS_RELOAD_COMMAND("commands.reload.command", "staffchatreload"),
    COMMANDS_RELOAD_ALIASES("commands.reload.alias", Arrays.asList("screload"));

    @Getter
    private String path;
    @Getter
    private Object value;
    private static BungeeStaffChat plugin = BungeeStaffChat.getInstance();
    @Setter
    private static YMLBase config;

    Config(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public static void load() {
        Arrays.stream(Config.values()).forEach(config -> {
            if (plugin.getConfigFile().getConfiguration().get(config.getPath()) == null) {
                plugin.getConfigFile().getConfiguration().set(config.getPath(), config.getValue());
            }
        });
        plugin.getConfigFile().save();
    }

    public boolean toBoolean() {
        return config.getBoolean(this.path);
    }

    public List<String> toList() {
        return config.getStringList(this.path);
    }

    public String toString() {
        return config.getString(this.path);
    }
}