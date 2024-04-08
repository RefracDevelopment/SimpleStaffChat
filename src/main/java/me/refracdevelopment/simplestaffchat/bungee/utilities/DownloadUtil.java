package me.refracdevelopment.simplestaffchat.bungee.utilities;

import net.byteflux.libby.BungeeLibraryManager;
import net.byteflux.libby.Library;
import net.md_5.bungee.api.plugin.Plugin;

public class DownloadUtil {

    public static void downloadAndEnable(Plugin plugin) {
        BungeeLibraryManager libraryManager = new BungeeLibraryManager(plugin);
        Library lib = Library.builder()
                .groupId("dev{}dejvokep") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
                .artifactId("boosted-yaml")
                .version("1.3.3")
                .build();

        libraryManager.addMavenCentral();
        libraryManager.loadLibrary(lib);
    }
}