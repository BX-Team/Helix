package org.bxteam.helix.database.wrapper;

import com.j256.ormlite.dao.Dao;
import org.bxteam.helix.database.DatabaseClient;
import org.bxteam.helix.database.function.SQLExceptionFunction;
import org.bxteam.helix.scheduler.Scheduler;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractOrmLiteDatabase {
    protected final DatabaseClient client;
    protected final Scheduler scheduler;

    protected AbstractOrmLiteDatabase(DatabaseClient client, Scheduler scheduler) {
        this.client = client;
        this.scheduler = scheduler;
    }

    protected <T> CompletableFuture<Dao.CreateOrUpdateStatus> save(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createOrUpdate(warp));
    }

    protected <T> CompletableFuture<T> saveIfNotExist(Class<T> type, T warp) {
        return this.action(type, dao -> dao.createIfNotExists(warp));
    }

    protected <T, ID> CompletableFuture<T> select(Class<T> type, ID id) {
        return this.action(type, dao -> dao.queryForId(id));
    }

    protected <T, ID> CompletableFuture<Optional<T>> selectSafe(Class<T> type, ID id) {
        return this.action(type, dao -> Optional.ofNullable(dao.queryForId(id)));
    }

    protected <T> CompletableFuture<Integer> delete(Class<T> type, T warp) {
        return this.action(type, dao -> dao.delete(warp));
    }

    protected <T> CompletableFuture<Integer> deleteAll(Class<T> type) {
        return this.action(type, dao -> dao.deleteBuilder().delete());
    }

    protected <T, ID> CompletableFuture<Integer> deleteById(Class<T> type, ID id) {
        return this.action(type, dao -> dao.deleteById(id));
    }

    protected <T> CompletableFuture<List<T>> selectAll(Class<T> type) {
        return this.action(type, Dao::queryForAll);
    }

    protected <T, ID, R> CompletableFuture<R> action(Class<T> type, SQLExceptionFunction<Dao<T, ID>, R> action) {
        CompletableFuture<R> completableFuture = new CompletableFuture<>();

        this.scheduler.runTaskAsynchronously(() -> {
            Dao<T, ID> dao = this.client.getDao(type);

            try {
                completableFuture.complete(action.apply(dao));
            } catch (Throwable throwable) {
                completableFuture.completeExceptionally(throwable);
            }
        });

        return completableFuture;
    }
}
