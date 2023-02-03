package me.refracdevelopment.simplestaffchat.bungee.config;

import lombok.Getter;
import lombok.Setter;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;

import java.util.Arrays;
import java.util.List;

/*
 * Taken from
 * https://github.com/BGHDDevelopment/PunishmentGUIRecode/blob/master/src/main/java/net/bghddevelopment/punishmentgui/language/Language.java
 * Modified for my needs
 */
public enum Config {

    STAFFCHAT_SYMBOL("staffchat-symbol", "@"),
    LUCKPERMS("luckperms", false),
    FORMAT_STAFFCHAT("format.minecraft-format", "%prefix% &e(%server%) &6&l%player%&7: &f%message%"),
    CONSOLE_STAFFCHAT("format.console-staffchat-format", "%prefix% &6&lConsole&7: &f%message%"),
    JOIN_ENABLED("join.enabled", false),
    JOIN_FORMAT("join.join-format", "&8[&a+&8] &9%player% &fjoined &e%server%&f."),
    SWITCH_FORMAT("join.switch-format", "&8[&b+&8] &9%player% &fswitched server &7(&e%from% &f%arrow% &e%server%&7)&f."),
    QUIT_FORMAT("join.quit-format", "&8[&c-&8] &9%player% &fleft &e%server%&f."),
    MESSAGES_PREFIX("messages.prefix", "&8[&4&lStaffChat&8]"),
    MESSAGES_NO_PERMISSION("messages.no-permission", "&cYou don't have permission to execute this command."),
    MESSAGES_RELOAD("messages.reload", "&7Config files reloaded. Changes should be live in-game."),
    MESSAGES_USAGE("messages.usage", "&c/staffchat <message>"),
    MESSAGES_TOGGLE_ON("messages.toggle-on", "%prefix% &7StaffChat toggled &aon&7."),
    MESSAGES_TOGGLE_OFF("messages.toggle-off", "%prefix% &7StaffChat toggled &coff&7."),
    MESSAGES_STAFFCHAT_OUTPUT("messages.staffchat-output", "default"),
    MESSAGES_STAFFCHAT_MESSAGE("messages.staffchat-message", Arrays.asList(
            "",
            "<g:#8A2387:#E94057:#F27121>SimpleStaffChat2 &8| &f Available Commands:",
            "",
            "&8- &d/staffchat <message> &7- Send staffchat messages.",
            "&8- &d/staffchattoggle &7- Send staffchat messages without needing to type a command.",
            "&8- &d/staffchatreload &7- Reload the config file.",
            ""
    ));

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
        if (plugin.getConfigFile() == null) return;
        Arrays.stream(Config.values()).forEach(config -> {
            if (plugin.getConfigFile().getConfiguration().get(config.getPath()) == null) {
                plugin.getConfigFile().getConfiguration().set(config.getPath(), config.getValue());
            }
        });
        plugin.getConfigFile().save();
        Color.log("&c==========================================");
        Color.log("&aAll files have been loaded correctly!");
        Color.log("&c==========================================");
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