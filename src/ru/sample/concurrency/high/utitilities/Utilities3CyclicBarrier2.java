package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 25/11/2018.
 */
class Utilities3CyclicBarrier2 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities3CyclicBarrier2.class.getSimpleName()
    );
    private static final int THREAD_COUNT = 3;

    static class ThreadWork implements Runnable {
        final CyclicBarrier cyclicBarrier;

        private ThreadWork(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                LOGGER.info("await");
                cyclicBarrier.await();
                LOGGER.info("done");
            } catch (BrokenBarrierException e) {
                LOGGER.throwing("ThreadWork", "run-broken", e);
            } catch (InterruptedException e) {
                LOGGER.throwing("ThreadWork", "run-interrupt", e);
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
                THREAD_COUNT + 1,
                () -> LOGGER.info("All thread on barrier")
        );
        Thread victim = new Thread(new ThreadWork(cyclicBarrier), "Victim");
        new Thread(new ThreadWork(cyclicBarrier), "hostage-1").start();
        new Thread(new ThreadWork(cyclicBarrier), "hostage-2").start();
        victim.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities3CyclicBarrier2", "main-1", e);
            return;
        }
        LOGGER.info("interrupt victim. barrier is broken " + cyclicBarrier.isBroken());
        victim.interrupt();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities3CyclicBarrier2", "main-2", e);
            return;
        }
        LOGGER.info("check broken barrier. is broken = " + cyclicBarrier.isBroken());
        new Thread(new ThreadWork(cyclicBarrier), "hostage-3").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities3CyclicBarrier2", "main-3", e);
            return;
        }
        cyclicBarrier.reset();
        LOGGER.info("reset barrier. is broken = " + cyclicBarrier.isBroken());
        new Thread(new ThreadWork(cyclicBarrier), "ok-1").start();
        new Thread(new ThreadWork(cyclicBarrier), "ok-2").start();
        new Thread(new ThreadWork(cyclicBarrier), "ok-3").start();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            LOGGER.throwing("Utilities3CyclicBarrier2", "main-4", e);
        }
    }
}