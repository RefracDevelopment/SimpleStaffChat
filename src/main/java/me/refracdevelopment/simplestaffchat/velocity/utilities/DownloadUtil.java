package me.refracdevelopment.simplestaffchat.velocity.utilities;

import me.refracdevelopment.simplestaffchat.velocity.VelocityStaffChat;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;

public class DownloadUtil {

    public static void downloadAndEnable() {
        VelocityLibraryManager libraryManager = new VelocityLibraryManager(VelocityStaffChat.getInstance().getLogger(),
                VelocityStaffChat.getInstance().getPath(), VelocityStaffChat.getInstance().getServer().getPluginManager(),
                VelocityStaffChat.getInstance());
        Library lib = Library.builder()
                .groupId("dev{}dejvokep") // "{}" is replaced with ".", useful to avoid unwanted changes made by maven-shade-plugin
                .artifactId("boosted-yaml")
                .version("1.3.3")
                .build();

        libraryManager.addMavenCentral();
        libraryManager.loadLibrary(lib);
    }
}