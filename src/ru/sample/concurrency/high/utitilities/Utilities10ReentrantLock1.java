package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities10ReentrantLock1 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities10ReentrantLock1.class.getSimpleName()
    );

    private static int shared = 0;

    static class Work implements Runnable {

        private final ReentrantLock reentrantLock;
        private final int value;

        Work(ReentrantLock reentrantLock, int value) {
            this.reentrantLock = reentrantLock;
            this.value = value;
        }

        public void run() {
            LOGGER.info("before lock");
            reentrantLock.lock();
            try {
                LOGGER.info("value was " + shared);
                shared = value;
                LOGGER.info("set " + value);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    LOGGER.throwing("Work", "run", e);
                }
            } finally {
                LOGGER.info("lock is released");
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        new Thread(new Work(reentrantLock, 10), "A").start();
        new Thread(new Work(reentrantLock, 100), "B").start();
    }
}