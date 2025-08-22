package org.bxteam.helix.logger.adapters;

import org.bxteam.helix.logger.LogAdapter;
import org.bxteam.helix.logger.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogAdapter implements LogAdapter {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void log(@NotNull LogLevel level, @NotNull String message) {
        log(level, message, null);
    }

    @Override
    public void log(@NotNull LogLevel level, @NotNull String message, @Nullable Throwable throwable) {
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);
        String logMessage = String.format("[%s] [%s] %s", timestamp, level.name(), message);

        PrintStream stream = (level == LogLevel.ERROR) ? System.err : System.out;
        stream.println(logMessage);

        if (throwable != null) {
            throwable.printStackTrace(stream);
        }
    }
}
