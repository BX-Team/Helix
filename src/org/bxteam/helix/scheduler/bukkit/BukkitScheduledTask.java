package org.bxteam.helix.scheduler.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bxteam.helix.scheduler.Task;

public class BukkitScheduledTask implements Task {
    BukkitTask task;
    boolean isRepeating;

    public BukkitScheduledTask(final BukkitTask task) {
        this.task = task;
        this.isRepeating = false;
    }

    public BukkitScheduledTask(final BukkitTask task, boolean isRepeating) {
        this.task = task;
        this.isRepeating = isRepeating;
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }

    @Override
    public boolean isCurrentlyRunning() {
        return Bukkit.getServer().getScheduler().isCurrentlyRunning(this.task.getTaskId());
    }

    @Override
    public boolean isRepeatingTask() {
        return isRepeating;
    }

    @Override
    public Plugin getPlugin() {
        return task.getOwner();
    }
}
