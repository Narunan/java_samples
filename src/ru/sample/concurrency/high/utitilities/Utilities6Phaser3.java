package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities6Phaser3 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities6Phaser3.class.getSimpleName()
    );

    private static class SportCar implements Runnable {

        private final CountDownLatch referee;
        private final Phaser phaser;
        private final int timeout;

        private SportCar(CountDownLatch referee, Phaser phaser, int timeout) {
            this.referee = referee;
            this.phaser = phaser;
            this.timeout = timeout;
        }

        public void run() {
            try {
                LOGGER.info("Is ready with timeout " + timeout);
                referee.countDown();
                referee.await();
            } catch (InterruptedException e) {
                LOGGER.throwing("SportCar", "run", e);
                return;
            }
            while (!Thread.currentThread().isInterrupted() && !phaser.isTerminated()) {
                int phase = phaser.arriveAndAwaitAdvance();
                if (phase < 0) {
                    LOGGER.info("I'm LOSE");
                    return;
                }
                LOGGER.info("arrive " + phase);
                try {
                    TimeUnit.SECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    LOGGER.throwing("SportCar", "run", e);
                }
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static class Stadium extends Phaser {

        private final int phases;

        private Stadium(int parties, int phaseCount) {
            super(parties);
            phases = phaseCount - 1;
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            boolean isDone = this.phases == phase || registeredParties == 0;
            if (isDone) {
                LOGGER.info("I'm WIN!");
            } else {
                LOGGER.info("I'm was first on " + phase + " loop. ");
            }
            return isDone;
        }
    }

    public static void main(String[] args) {
        Random random = ThreadLocalRandom.current();
        CountDownLatch refery = new CountDownLatch(3);
        Phaser phaser = new Stadium(3, 4);

        Thread first = new Thread(new SportCar(refery, phaser, random.nextInt(3)), "Alex");
        Thread second = new Thread(new SportCar(refery, phaser, random.nextInt(3)), "Valentin");
        Thread third = new Thread(new SportCar(refery, phaser, random.nextInt(3)), "John");

        first.start();
        second.start();
        third.start();

        LOGGER.info("GO!");

        try {
            first.join();
            second.join();
            third.join();

            LOGGER.info("All car is finished");
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities6Phaser3", "main", e);
        }
    }
}