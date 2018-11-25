package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities6Phaser2 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities6Phaser2.class.getSimpleName()
    );

    private static final int THREAD_COUNT = 3;

    static class PhaserThread implements Runnable {
        private final Phaser phaser;

        private PhaserThread(Phaser phaser) {
            this.phaser = phaser;
        }

        public void run() {
            LOGGER.info("before advance");
            phaser.arriveAndAwaitAdvance();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.throwing("PhaserThread", "run", e);
                return;
            }
            LOGGER.info("before deregister");
            phaser.arriveAndDeregister();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Phaser phaser = new Phaser(4 /* main thread + THREAD_COUNT */);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(new PhaserThread(phaser));
        }

        LOGGER.info("before advance");
        phaser.arriveAndAwaitAdvance();

        LOGGER.info("before deregister");
        phaser.arriveAndDeregister();

        executorService.shutdown();
    }
}