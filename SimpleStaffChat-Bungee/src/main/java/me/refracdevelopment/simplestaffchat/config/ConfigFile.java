package me.refracdevelopment.simplestaffchat.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import lombok.Getter;
import me.refracdevelopment.simplestaffchat.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.utilities.chat.RyMessageUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public class ConfigFile {

    private YamlDocument configFile;

    public ConfigFile(String name) {
        try {
            configFile = YamlDocument.create(new File(SimpleStaffChat.getInstance().getDataFolder(), name),
                    getClass().getResourceAsStream("/" + name),
                    GeneralSettings.builder().setUseDefaults(false).build(),
                    LoaderSettings.builder().setAutoUpdate(false).build()
            );

            configFile.update();
            configFile.save();
        } catch (IOException exception) {
            RyMessageUtils.sendPluginError("Failed to load config file! Shutting down...", exception, true);
            SimpleStaffChat.getInstance().getProxy().getPluginManager().unregisterCommands(SimpleStaffChat.getInstance());
            SimpleStaffChat.getInstance().getProxy().getPluginManager().unregisterListeners(SimpleStaffChat.getInstance());
        }
    }

    public void save() {
        try {
            configFile.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            configFile.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String path) {
        return configFile.getInt(path, 0);
    }

    public double getDouble(String path) {
        return configFile.getDouble(path, 0.0);
    }

    public boolean getBoolean(String path) {
        return configFile.getBoolean(path, false);
    }

    public String getString(String path, boolean check) {
        return configFile.getString(path, null);
    }

    public String getString(String path) {
        if (configFile.contains(path)) {
            return configFile.getString(path, "String at path '" + path + "' not found.").replace("|", "\u2503");
        }

        return null;
    }

    public List<String> getStringList(String path) {
        return configFile.getStringList(path);
    }

    public List<String> getStringList(String path, boolean check) {
        if (!configFile.contains(path)) return null;
        return getStringList(path);
    }
}