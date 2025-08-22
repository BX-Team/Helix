package org.bxteam.helix;

import org.bukkit.plugin.java.JavaPlugin;
import org.bxteam.helix.scheduler.Scheduler;
import org.bxteam.helix.scheduler.bukkit.BukkitScheduler;
import org.bxteam.helix.scheduler.folia.FoliaScheduler;
import org.bxteam.helix.scheduler.paper.PaperScheduler;

public class ServerSoftware {
    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isPaper() {
        try {
            Class.forName("io.papermc.paper.event.player.AsyncChatEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isBukkit() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Scheduler getScheduler(JavaPlugin plugin) {
        if (isFolia()) {
            return new FoliaScheduler(plugin);
        } else if (isPaper()) {
            return new PaperScheduler(plugin);
        }

        return new BukkitScheduler(plugin);
    }
}
