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
class Utilities6Phaser1 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities6Phaser1.class.getSimpleName()
    );

    private static final int THREAD_COUNT = 3;

    static class PhaserThread implements Runnable {
        private final Phaser phaser;

        private PhaserThread(Phaser phaser) {
            this.phaser = phaser;
            // increment phaser parties
            this.phaser.register();
        }

        public void run() {
            LOGGER.info("before advance 0");
            phaser.arriveAndAwaitAdvance();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.throwing("PhaserThread", "run", e);
                return;
            }
            LOGGER.info("before advance 1");
            phaser.arriveAndAwaitAdvance();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.throwing("PhaserThread", "run", e);
                return;
            }
            LOGGER.info("before deregister");
            // decrement phaser parties
            phaser.arriveAndDeregister();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Phaser phaser = new Phaser(1 /* main thread */);
        int currentPhase;

        LOGGER.info("start");

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(new PhaserThread(phaser));
        }

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        LOGGER.info("done " + currentPhase);

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        LOGGER.info("done " + currentPhase);

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        LOGGER.info("done " + currentPhase);

        phaser.arriveAndDeregister();

        if (phaser.isTerminated()) {
            LOGGER.info("phaser is terminated");
        }
        executorService.shutdown();
    }
}