package ru.sample.concurrency.high.utitilities.complex;

import ru.sample.logger.LoggerUtils;

import java.time.temporal.ValueRange;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Semaphore1 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(Semaphore1.class.getSimpleName());
    private static final ManualCarWash MANUAL_CAR_WASH = new ManualCarWash();
    private static final int MAX_DRIVERS = 20;
    private static final int SECONDS_OF_THE_DAY = 10;

    private static class ManualCarWash {
        private final Semaphore carBox = new Semaphore(5);

        void wash(Driver driver) {
            try {
                LOGGER.info("Can I wash?");
                carBox.acquire();
                try {
                    LOGGER.info("I CAN Wash!!");
                    driver.doCarClean();
                } finally {
                    carBox.release();
                }
            } catch (InterruptedException e) {
                driver.goHome();
                LOGGER.throwing("ManualCarWash", "wash", e);
            }

        }

        void tryWash(Driver driver) {
            LOGGER.info("Can I try to wash?");
            if (carBox.tryAcquire()) {
                try {
                    LOGGER.info("I'am try and I can!");
                    driver.doCarClean();
                } catch (InterruptedException e) {
                    driver.goHome();
                    LOGGER.throwing("ManualCarWash", "tryWash", e);
                } finally {
                    carBox.release();
                }
            }
        }
    }

    private static class Driver implements Runnable {

        private final ThreadLocalRandom random = ThreadLocalRandom.current();
        private final Car car = new Car();

        @Override
        public void run() {
            while (!isTimeToGoHome()) {
                try {
                    car.doDrive(random.nextInt(500));
                    TimeUnit.SECONDS.sleep(1);
                    if (car.isNotClean()) {
                        if (car.isVeryDirty()) {
                            MANUAL_CAR_WASH.wash(this);
                            LOGGER.info("My very dirty car is clean!");
                        } else {
                            MANUAL_CAR_WASH.tryWash(this);
                            LOGGER.info(car.isNotClean() ? ":(" : ":)");
                        }

                    }
                } catch (InterruptedException e) {
                    goHome();
                    LOGGER.throwing("Driver", "run", e);
                }
            }
        }

        private void doCarClean() throws InterruptedException {
            LOGGER.info("doCarClean");
            car.doClean();
        }

        void goHome() {
            LOGGER.info("It's time to go home");
            Thread.currentThread().interrupt();
        }

        boolean isTimeToGoHome() {
            return Thread.currentThread().isInterrupted();
        }

    }

    private static class Car {

        private static final Function<Long, Long> CONTAMINATION_CALC = (distance) -> distance / 100L;
        private static final ValueRange DIRTY_CONTAMINATION_RANGE = ValueRange.of(0L, 20L, 5L, 40L); //long long;
        private long contaminationLevel;

        void doDrive(long distance) {
            contaminationLevel += CONTAMINATION_CALC.apply(distance);
            if (contaminationLevel > DIRTY_CONTAMINATION_RANGE.getMaximum()) {
                contaminationLevel = DIRTY_CONTAMINATION_RANGE.getMaximum();
            }
        }

        void doClean() throws InterruptedException {
            TimeUnit.SECONDS.sleep(5);
            contaminationLevel = DIRTY_CONTAMINATION_RANGE.getMinimum();
        }

        boolean isNotClean() {
            return contaminationLevel >= DIRTY_CONTAMINATION_RANGE.getSmallestMaximum();
        }

        boolean isVeryDirty() {
            return contaminationLevel >= DIRTY_CONTAMINATION_RANGE.getLargestMinimum();
        }
    }

    public static void main(String[] args) {
        AtomicInteger driverId = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_DRIVERS,
                (runnable) -> new Thread(runnable, "Driver-" + driverId.incrementAndGet())
        );
        for (int i = 0; i < MAX_DRIVERS; i++) {
            executorService.execute(new Driver());
        }
        try {
            TimeUnit.SECONDS.sleep(SECONDS_OF_THE_DAY);
        } catch (InterruptedException e) {
            LOGGER.throwing("Semaphore1", "main", e);
        }
        LOGGER.info("shutdown");
        executorService.shutdownNow();
        LOGGER.info("shutdown is done");
    }
}
