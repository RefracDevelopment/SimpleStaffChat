package me.refracdevelopment.simplestaffchat.spigot.utilities;

public class FoliaUtil {

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServerInitEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}