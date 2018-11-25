package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities2CountDownLatch {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities2CountDownLatch.class.getSimpleName()
    );
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        CountDownLatch initCountDownLatch = new CountDownLatch(1);
        CountDownLatch workCountDownLatch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                LOGGER.info("READY");
                try {
                    initCountDownLatch.await();
                    try {
                        LOGGER.info("DO-WORK");
                        TimeUnit.SECONDS.sleep(3);
                    } finally {
                        LOGGER.info("DONE");
                        workCountDownLatch.countDown();
                    }
                } catch (InterruptedException e) {
                    LOGGER.throwing("Utilities2CountDownLatch", "initCountDownLatch-await", e);
                }
            });
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities2CountDownLatch", "main-sleep", e);
        }
        LOGGER.info("All workers created");
        initCountDownLatch.countDown();

        try {
            LOGGER.info("Waiting");
            workCountDownLatch.await();
            LOGGER.info("All work is done");
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities2CountDownLatch", "workCountDownLatch.await", e);
        }
        executorService.shutdown();
    }
}