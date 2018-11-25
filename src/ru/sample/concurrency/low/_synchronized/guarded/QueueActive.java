package ru.sample.concurrency.low._synchronized.guarded;

import ru.sample.logger.LoggerUtils;

import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class QueueActive {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(QueueActive.class.getSimpleName());

    private int data = 0;

    private void get() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                LOGGER.info("check get");
                if (this.data != 0) {
                    LOGGER.info("success get");
                    this.data = 0;
                    return;
                }
            }
        }
    }

    private void set(int data) {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                LOGGER.info("check set");
                if (this.data == 0) {
                    LOGGER.info("success set");
                    this.data = data;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        QueueActive queueActive = new QueueActive();
        Thread setter = new Thread(() -> {
            for (int i = 1; i <= 50 && !Thread.currentThread().isInterrupted(); i++) {
                queueActive.set(i);
            }
        }, "Setter");
        Thread getter = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                queueActive.get();
            }
        }, "Getter");
        getter.setDaemon(true);
        setter.start();
        getter.start();
    }
}
