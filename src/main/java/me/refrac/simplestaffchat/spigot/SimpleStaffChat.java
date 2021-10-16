package me.refrac.simplestaffchat.spigot;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SimpleStaffChat extends JavaPlugin {
    @Getter
    private static SimpleStaffChat instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadCommands() {

    }

    private void loadListeners() {

    }
}
