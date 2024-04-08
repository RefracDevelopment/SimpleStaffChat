package me.refracdevelopment.simplestaffchat.spigot.utilities;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import org.bukkit.plugin.java.JavaPlugin;

public class DownloadUtil {

    public static void downloadAndEnable(JavaPlugin plugin) {
        BukkitLibraryManager libraryManager = new BukkitLibraryManager(plugin);
        Library lib = Library.builder()
                .groupId("dev{}dejvokep") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
                .artifactId("boosted-yaml")
                .version("1.3.3")
                .build();

        libraryManager.addMavenCentral();
        libraryManager.loadLibrary(lib);
    }
}