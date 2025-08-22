package org.bxteam.helix.scheduler.paper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bxteam.helix.scheduler.folia.FoliaScheduler;

public class PaperScheduler extends FoliaScheduler {
    public PaperScheduler(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean isGlobalThread() {
        return Bukkit.getServer().isPrimaryThread();
    }
}
