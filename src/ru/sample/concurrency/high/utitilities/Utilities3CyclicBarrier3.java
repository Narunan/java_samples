package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 25/11/2018.
 */
class Utilities3CyclicBarrier3 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities3CyclicBarrier3.class.getSimpleName()
    );

    private static final int THREAD_COUNT = 2;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
                THREAD_COUNT + 1,
                () -> LOGGER.info("All thread on barrier")
        );
        Thread t1 = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                LOGGER.throwing("AnonymRunnable", "run", e);
            }
        }, "First");

        Thread t2 = new Thread(() -> {
            try {
                cyclicBarrier.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                LOGGER.throwing("AnonymRunnable", "run", e);
            }
        }, "Second");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities3CyclicBarrier3", "main", e);
        }
        LOGGER.info("barrier is broken = " + cyclicBarrier.isBroken());
    }
}