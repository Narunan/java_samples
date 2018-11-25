package ru.sample.concurrency.low._synchronized.guarded;

import ru.sample.logger.LoggerUtils;

import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class WaitNotify {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(WaitNotify.class.getSimpleName());
    private static final Object LOCK = new Object();
    private int data = 0;

    private void set(int value) {
        synchronized (LOCK) {
            LOGGER.info("inside with " + value);
            if (data != 0) {
                try {
                    LOGGER.info("set wait");
                    LOCK.wait();
                    LOGGER.info("wakeUp");
                } catch (InterruptedException e) {
                    LOGGER.throwing("WaitNotify", "set", e);
                    return;
                }
            }
            data = value;
            LOGGER.info("put " + value);
            LOGGER.info("notify");
            LOCK.notify();
        }
    }

    private void get() {
        synchronized (LOCK) {
            LOGGER.info("inside with " + data);
            while (data == 0) {
                try {
                    LOGGER.info("get wait");
                    LOCK.wait();
                    LOGGER.info("wakeUp");
                } catch (InterruptedException e) {
                    LOGGER.throwing("WaitNotify", "get", e);
                    return;
                }
            }
            LOGGER.info("get " + data);
            data = 0;
            LOCK.notify();
            LOGGER.info("notify");
        }
    }

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify();
        Thread setter = new Thread(() -> {
            for (int i = 1; i <= 50 && !Thread.currentThread().isInterrupted(); i++) {
                waitNotify.set(i);
            }
        }, "Setter");
        Thread getter = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                waitNotify.get();
            }
        }, "Getter");
        getter.setDaemon(true);
        setter.start();
        getter.start();
    }
}
