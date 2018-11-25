package ru.sample.concurrency.high.utitilities.complex;

import ru.sample.logger.LoggerUtils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Semaphore2 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Semaphore2.class.getSimpleName(), Level.INFO
    );
    private static final FitnessHouse FITNESS_HOUSE = new FitnessHouse();
    private static final int MAX_GYM_BUNNIES = 60;
    private static final int SECONDS_OF_THE_DAY = 60;

    private static class FitnessHouse {
        private static final Semaphore SAUNA = new Semaphore(10);
        private static final Semaphore POOL = new Semaphore(40);
        private static final Semaphore SHOWER_ROOM = new Semaphore(30);

        void doVisit(GymBunny gymBunny) throws InterruptedException {
            LOGGER.info("IN FITNESS_HOUSE");
            wash(gymBunny, "POOL");
            swim(gymBunny);
            warmUp(gymBunny);
            wash(gymBunny, "HOME");
            LOGGER.info("OUT FITNESS_HOUSE");
        }

        private void wash(GymBunny gymBunny, String reason) throws InterruptedException {
            if (SHOWER_ROOM.tryAcquire(1, 5, TimeUnit.SECONDS)) {
                try {
                    LOGGER.info("IN SHOWER_ROOM for " + reason);
                    TimeUnit.SECONDS.sleep(1);
                } finally {
                    SHOWER_ROOM.release();
                    LOGGER.info("OUT SHOWER_ROOM for " + reason);
                }
            } else {
                gymBunny.upset("SHOWER_ROOM");
            }
        }

        private void swim(GymBunny gymBunny) throws InterruptedException {
            int permitsByFatness = getPermitsByFatness(gymBunny);
            if (POOL.tryAcquire(permitsByFatness, 5, TimeUnit.SECONDS)) {
                try {
                    LOGGER.info("IN POOL");
                    TimeUnit.SECONDS.sleep(3);
                } finally {
                    POOL.release(permitsByFatness);
                    LOGGER.info("OUT POOL");
                }
            } else {
                gymBunny.upset("POOL");
            }
        }

        private void warmUp(GymBunny gymBunny) throws InterruptedException {
            int permitsByFatness = getPermitsByFatness(gymBunny);
            if (SAUNA.tryAcquire(permitsByFatness, 5, TimeUnit.SECONDS)) {
                try {
                    LOGGER.info("IN SAUNA");
                    TimeUnit.SECONDS.sleep(2);
                } finally {
                    LOGGER.info("OUT SAUNA");
                    SAUNA.release(permitsByFatness);
                }
            } else {
                gymBunny.upset("SAUNA");
            }
        }

        private int getPermitsByFatness(GymBunny gymBunny) {
            return gymBunny.isGraceful() ? 1 : 2;
        }
    }

    private static class GymBunny implements Runnable {
        private final AtomicInteger losers;
        private int fatness;
        private int tolerance;
        private int visitCount;

        GymBunny(AtomicInteger losers, int fatness) {
            this.losers = losers;
            this.fatness = fatness;
        }

        boolean isGraceful() {
            return fatness < 50;
        }

        void upset(String place) {
            LOGGER.info(place + " is make me sad");
            tolerance--;
        }

        boolean isVerySad() {
            return tolerance < 2;
        }

        @Override
        public void run() {
            try {
                LOGGER.info("My fatness is " + fatness);
                while (!isGraceful()) {
                    try {
                        tolerance = 5;
                        FITNESS_HOUSE.doVisit(this);
                        visitCount++;
                        if (isVerySad()) {
                            LOGGER.info("Horrible Gym!");
                            return;
                        } else {
                            fatness -= 5;
                            LOGGER.info("My current fatness is " + fatness);
                        }
                    } catch (InterruptedException e) {
                        LOGGER.throwing("GymBunny", "run", e);
                        return;
                    }
                }
            } finally {
                lastWord();
            }
        }

        private void lastWord() {
            boolean graceful = isGraceful();
            LOGGER.info("I'm was in fithess house " + visitCount + " count and i'am is "
                    + (graceful ? "Graceful <3" : "FAT((("));
            if (visitCount == 0 && !graceful) {
                losers.incrementAndGet();
                LOGGER.warning("Very very very sad");
            }
        }
    }

    public static void main(String[] args) {
        AtomicInteger gymBunnyId = new AtomicInteger(0);
        AtomicInteger losers = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_GYM_BUNNIES,
                (runnable) -> new Thread(runnable, "GymBunny-" + gymBunnyId.incrementAndGet())
        );
        Random random = new Random();
        for (int i = 0; i < MAX_GYM_BUNNIES; i++) {
            executorService.execute(new GymBunny(losers, random.nextInt(300)));
        }
        try {
            TimeUnit.SECONDS.sleep(SECONDS_OF_THE_DAY);
        } catch (InterruptedException e) {
            LOGGER.throwing("Semaphore2", "main", e);
        }
        LOGGER.info("shutdown");
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
            LOGGER.info("All gym bunnies is " + MAX_GYM_BUNNIES + ", losers count is " + losers.get());
        } catch (InterruptedException e) {
            LOGGER.throwing("Semaphore2", "main", e);
        }
    }
}