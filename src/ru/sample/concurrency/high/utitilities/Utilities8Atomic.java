package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities8Atomic {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities8Atomic.class.getSimpleName()
    );

    private static final AtomicInteger SHARED_ATOMIC = new AtomicInteger(0);

    static class Work implements Runnable {

        public void run() {
            LOGGER.info("run");
            int count = 1;
            while (!Thread.currentThread().isInterrupted()) {
                if (SHARED_ATOMIC.compareAndSet(0, count)) {
                    LOGGER.info("set " + count);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        LOGGER.throwing("Work", "run", e);
                        return;
                    } finally {
                        SHARED_ATOMIC.set(0);
                    }
                    count++;
                    if (count == 4) {
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Work(), "A").start();
        new Thread(new Work(), "B").start();
        new Thread(new Work(), "C").start();
    }
}