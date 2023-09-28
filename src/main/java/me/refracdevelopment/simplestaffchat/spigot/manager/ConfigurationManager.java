package me.refracdevelopment.simplestaffchat.spigot.manager;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.config.CommentedFileConfiguration;
import dev.rosewood.rosegarden.config.RoseSetting;
import dev.rosewood.rosegarden.manager.AbstractConfigurationManager;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;

import java.util.Arrays;

public class ConfigurationManager extends AbstractConfigurationManager {

    public enum Setting implements RoseSetting {
        // Config Settings
        BUNGEECORD("bungeecord", false, "Enable this to allow messages", "To be sent using the BungeeCord", "Plugin Messaging Protocol", "This allows Velocity to receive staffchat messages"),
        SYMBOLS("symbols", true, "Change to false if you want to disable all chat symbols."),
        SERVER_NAME("server-name", "hub", "This is so stuff like Discord can get the server name"),
        STAFFCHAT_SYMBOL("staffchat-symbol", "#", "Used to send messages to staffchat without the", "need to type in commands", "this uses the same format as /staffchat <message>"),
        ADMINCHAT_SYMBOL("adminchat-symbol", "@"),
        DEVCHAT_SYMBOL("devchat-symbol", "%"),
        MINECRAFT_FORMAT("minecraft-format", "&8[&c&lStaffChat&8] &6&l%player%&7: &f%message%", "Minecraft format"),
        CONSOLE_FORMAT("console-format", "&8[&c&lStaffChat&8] &6&lConsole&7: &f%message%", "Format for messages sent by the console", "PlaceholderAPI is not supported here"),
        ADMINCHAT_FORMAT("adminchat-format", "&8[&4&lAdminChat&8] &6&l%player%&7: &f%message%", "AdminChat format"),
        CONSOLE_ADMINCHAT_FORMAT("console-adminchat-format", "&8[&4&lAdminChat&8] &6&lConsole&7: &f%message%", "Format for messages sent by the console", "PlaceholderAPI is not supported here"),
        DEVCHAT_FORMAT("devchat-format", "&8[&b&lDevChat&8] &6&l%player%&7: &f%message%", "DevChat format"),
        CONSOLE_DEVCHAT_FORMAT("console-devchat-format", "&8[&b&lDevChat&8] &6&lConsole&7: &f%message%", "Format for messages sent by the console", "PlaceholderAPI is not supported here"),
        JOIN_ENABLED("join.enabled", false, "This is for staff joins only", "permission: simplestaffchat.join or simplestaffchat.quit"),
        JOIN_FORMAT("join.join-format", "&8[&a+&8] &9%player% &fjoined&f."),
        JOIN_QUIT_FORMAT("join.quit-format", "&8[&c-&8] &9%player% &fleft&f."),
        STAFFCHAT_OUTPUT("staffchat-output", "default", "If this is set to \"custom\" you can change the /sc output message to anything", "If this is set to \"toggle\" you can use /sc to toggle into staffchat easier"),
        STAFFCHAT_MESSAGE("staffchat-message", Arrays.asList(
                "",
                "<g:#8A2387:#E94057:#F27121>SimpleStaffChat &8| &fAvailable Commands:",
                "&8- &d/staffchat <message> &7- Allows you to send messages.",
                "&8- &d/staffchattoggle &7- Send staffchat messages without needing to type a command.",
                "&8- &d/staffchatreload &7- Reloads the config files.",
                ""
        ));

        private final String key;
        private final Object defaultValue;
        private final String[] comments;
        private Object value = null;

        Setting(String key, Object defaultValue, String... comments) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.comments = comments != null ? comments : new String[0];
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public Object getDefaultValue() {
            return this.defaultValue;
        }

        @Override
        public String[] getComments() {
            return this.comments;
        }

        @Override
        public Object getCachedValue() {
            return this.value;
        }

        @Override
        public void setCachedValue(Object value) {
            this.value = value;
        }

        @Override
        public CommentedFileConfiguration getBaseConfig() {
            return SimpleStaffChat.getInstance().getManager(ConfigurationManager.class).getConfig();
        }
    }

    public ConfigurationManager(RosePlugin rosePlugin) {
        super(rosePlugin, Setting.class);
    }

    @Override
    protected String[] getHeader() {
        return new String[]{
                "  ___________                __           _________ __           _____  ______________  /\\             __   ",
                " /   _____/__| _____ ______ |  |   ____  /   _____//  |______  _/ ____\\/ ____\\_   ___ \\|  |__ _____  _/  |_ ",
                " \\_____  \\|  |/     \\\\____ \\|  | _/ __ \\ \\_____  \\\\   __\\__  \\ \\   __\\\\   __\\/    \\  \\/|  |  \\\\__  \\ \\   __\\",
                " /        \\  |  | |  \\  |_\\ \\  |__  ___/_/        \\|  |  / __ \\_|  |   |  |  \\     \\____      \\/ __ \\_|  |  ",
                "/_______  /__|__|_|  /   ___/____/\\___  /_______  /|__| (____  /|_ |   |_ |   \\______  /___|  /____  /|__|  ",
                "        \\/         \\/|__|             \\/        \\/           \\/   \\/     \\/          \\/     \\/     \\/       "
        };
    }

}