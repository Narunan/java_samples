package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class Utilities1Semaphore {

    private static final int THREAD_COUNT = 10;
    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities1Semaphore.class.getSimpleName()
    );

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(THREAD_COUNT / 2);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    LOGGER.throwing("Utilities1Semaphore", "await", e);
                    return;
                }
                try {
                    semaphore.acquire();
                    try {
                        LOGGER.info("IN");
                        TimeUnit.SECONDS.sleep(5);
                    } finally {
                        semaphore.release();
                        LOGGER.info("OUT");
                    }
                } catch (InterruptedException e) {
                    LOGGER.throwing("Utilities1Semaphore", "acquire", e);
                }
            });
        }
        countDownLatch.countDown();
        executorService.shutdown();
    }
}
