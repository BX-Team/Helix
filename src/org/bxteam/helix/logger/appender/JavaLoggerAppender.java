package org.bxteam.helix.logger.appender;

import org.bxteam.helix.logger.LogEntry;
import org.bxteam.helix.logger.LogLevel;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Appender that outputs log entries to Java's built-in logging framework.
 */
public class JavaLoggerAppender implements Appender {
    private final Logger logger;

    /**
     * Constructs a JavaLoggerAppender with the specified Java Logger.
     *
     * @param logger the Java Logger instance to use
     */
    public JavaLoggerAppender(@NotNull Logger logger) {
        this.logger = requireNonNull(logger, "logger");
    }

    /**
     * Constructs a JavaLoggerAppender with a logger for the specified name.
     *
     * @param loggerName the name of the logger
     */
    public JavaLoggerAppender(@NotNull String loggerName) {
        this.logger = Logger.getLogger(requireNonNull(loggerName, "loggerName"));
    }

    /**
     * Appends the log entry to the Java Logger.
     *
     * @param entry the log entry to append
     */
    @Override
    public void append(LogEntry entry) {
        Level javaLevel = convertLevel(entry.logLevel());
        String message = entry.message();

        if (entry.throwable() != null) {
            logger.log(javaLevel, message, entry.throwable());
        } else {
            logger.log(javaLevel, message);
        }
    }

    /**
     * Closes the appender and releases any resources.
     */
    @Override
    public void close() {
        // No resources to close.
    }

    /**
     * Converts our LogLevel to Java's logging Level.
     *
     * @param level the LogLevel to convert
     * @return the corresponding Java Level
     */
    private Level convertLevel(LogLevel level) {
        return switch (level) {
            case DEBUG -> Level.FINE;
            case INFO -> Level.INFO;
            case WARN -> Level.WARNING;
            case ERROR -> Level.SEVERE;
        };
    }
}
