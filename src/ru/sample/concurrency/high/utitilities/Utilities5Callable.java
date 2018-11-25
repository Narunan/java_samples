package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities5Callable {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities5Callable.class.getSimpleName()
    );

    static class CallableWork implements Callable<Integer> {

        private final int timeoutInSec;
        private final int result;

        CallableWork(int timeoutInSec, int result) {
            this.timeoutInSec = timeoutInSec;
            this.result = result;
        }

        @Override
        public Integer call() {
            try {
                LOGGER.info("start with " + timeoutInSec + " and result " + result);
                TimeUnit.SECONDS.sleep(timeoutInSec);
            } catch (InterruptedException e) {
                LOGGER.throwing("CallableWork", "call", e);
            } finally {
                LOGGER.info("finish");
            }
            return result;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        LOGGER.info("start");
        Future<Integer> one = executorService.submit(new CallableWork(3, 1));
        Future<Integer> two = executorService.submit(new CallableWork(3, 2));
        Future<Integer> three = executorService.submit(new CallableWork(2, 3));

        try {
            LOGGER.info("three result = " + three.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.throwing("Utilities5Callable", "main", e);
        } catch (TimeoutException expected) {
            LOGGER.info("TimeoutException was expected");
        }
        try {
            LOGGER.info("one = " + one.get());
            LOGGER.info("two = " + two.get());
            LOGGER.info("three = " + three.get());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.throwing("Utilities5Callable", "main", e);
        }
        executorService.shutdown();
        LOGGER.info("done");
    }
}