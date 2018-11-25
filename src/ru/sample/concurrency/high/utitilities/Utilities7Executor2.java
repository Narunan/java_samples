package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities7Executor2 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities7Executor2.class.getSimpleName()
    );

    private static final int THREAD_COUNT = 1;

    static class Work implements Runnable {

        private final AtomicInteger counter;

        Work(AtomicInteger counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            int count = counter.decrementAndGet();
            LOGGER.info("done: " + count);

        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_COUNT);

        LOGGER.info("start");
        ScheduledFuture<Integer> callable = executor.schedule(() -> {
            int result = ThreadLocalRandom.current().nextInt(10);
            LOGGER.info("First task is done with result = " + result);
            return result;
        }, 2, TimeUnit.SECONDS);
        int executionCount;
        try {
            executionCount = callable.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.throwing("Utilities7Executor2", "run", e);
            return;
        }
        AtomicInteger counter = new AtomicInteger(executionCount);
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(
                new Work(counter), 1, 2, TimeUnit.SECONDS
        );
        long taskDelay = scheduledFuture.getDelay(TimeUnit.SECONDS);
        while (counter.get() != 0) {
            try {
                TimeUnit.SECONDS.sleep(taskDelay);
            } catch (InterruptedException e) {
                LOGGER.throwing("Utilities7Executor2", "run", e);
            }
        }
        LOGGER.info("finish");
        scheduledFuture.cancel(false);
        executor.shutdown();
    }
}