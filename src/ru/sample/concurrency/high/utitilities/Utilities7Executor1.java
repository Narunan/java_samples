package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities7Executor1 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities7Executor1.class.getSimpleName()
    );

    private static final int THREAD_COUNT = 2;
    private static final int WORK_COUNT = THREAD_COUNT * 3;

    static class Worker implements Runnable {

        private final int workId;

        Worker(int workId) {
            this.workId = workId;
        }

        @Override
        public void run() {
            try {
                LOGGER.info("do work — " + workId);
                TimeUnit.SECONDS.sleep(2);
                LOGGER.info("work " + workId + " is done");
            } catch (InterruptedException e) {
                LOGGER.throwing("Worker", "run", e);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int workId = 0; workId < WORK_COUNT; workId++) {
            executorService.submit(new Worker(workId));
        }
        executorService.shutdown();
    }
}