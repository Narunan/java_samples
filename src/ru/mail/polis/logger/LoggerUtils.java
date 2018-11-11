package ru.mail.polis.logger;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtils {

    public static Logger getFormattedLogger(String name) {
        Logger logger = Logger.getLogger(name);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String FORMAT = "[%1$tF %1$tT] [%2$-7s] %3$s: %4$s %n";
            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(FORMAT,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        Thread.currentThread().getName(),
                        lr.getMessage()
                );
            }
        });
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        return logger;
    }
}
