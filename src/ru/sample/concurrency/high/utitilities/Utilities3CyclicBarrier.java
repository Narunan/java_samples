package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities3CyclicBarrier {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities3CyclicBarrier.class.getSimpleName()
    );
    private static final int THREAD_COUNT = 3;

    static class ThreadWork implements Runnable {
        final CyclicBarrier cyclicBarrier;

        private ThreadWork(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                LOGGER.info("stage - 0");
                cyclicBarrier.await();
                LOGGER.info("stage - 1");
                cyclicBarrier.await();
                LOGGER.info("stage - 2");
            } catch (BrokenBarrierException | InterruptedException e) {
                LOGGER.throwing("ThreadWork", "run", e);
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
                THREAD_COUNT,
                () -> LOGGER.info("All thread on barrier")
        );
        new Thread(new ThreadWork(cyclicBarrier)).start();
        new Thread(new ThreadWork(cyclicBarrier)).start();
        new Thread(new ThreadWork(cyclicBarrier)).start();
    }
}