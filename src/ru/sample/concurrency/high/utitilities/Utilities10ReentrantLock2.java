package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities10ReentrantLock2 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities10ReentrantLock2.class.getSimpleName()
    );

    private static final int WORK_COUNT = 20;
    private static final int PRODUCER_THREAD_COUNT = 2;
    private static final int CONSUMER_THREAD_COUNT = 2;

    private static class Producer implements Runnable {

        private final ThreadSafeQueue<Integer> queue;
        private final int from;
        private final int to;

        Producer(ThreadSafeQueue<Integer> queue, int from, int to) {
            this.queue = queue;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            for (int data = from; data < to && !Thread.currentThread().isInterrupted(); data++) {
                try {
                    LOGGER.info("set " + data);
                    queue.set(data);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.throwing("Producer", "run", e);
                }
            }
        }
    }

    private static class Consumer implements Runnable {

        private final ThreadSafeQueue<Integer> queue;

        Consumer(ThreadSafeQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Integer data = queue.get();
                    LOGGER.info("get " + data);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.throwing("Consumer", "run", e);
                }
            }
        }
    }

    private static final class ThreadSafeQueue<T> {

        private final ReentrantLock lock;
        private final Condition empty;
        private final Condition full;
        private T data;

        ThreadSafeQueue() {
            lock = new ReentrantLock();
            empty = lock.newCondition();
            full = lock.newCondition();
        }

        T get() throws InterruptedException {
            lock.lock();
            try {
                while (this.data == null) {
                    full.await();
                }
                T result = this.data;
                this.data = null;
                empty.signal();
                return result;
            } finally {
                lock.unlock();
            }
        }

        void set(T data) throws InterruptedException {
            lock.lock();
            try {
                while (this.data != null) {
                    empty.await();
                }
                this.data = data;
                full.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    private static class NamedThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, namePrefix + threadNumber.getAndIncrement());
        }
    }

    public static void main(String[] args) {
        ThreadSafeQueue<Integer> queue = new ThreadSafeQueue<>();
        ExecutorService producers = Executors.newFixedThreadPool(
                PRODUCER_THREAD_COUNT,
                new NamedThreadFactory("Producer-")
        );
        ExecutorService consumers = Executors.newFixedThreadPool(
                CONSUMER_THREAD_COUNT,
                new NamedThreadFactory("Consumer-")
        );
        for (int i = 0; i < PRODUCER_THREAD_COUNT; i++) {
            producers.submit(new Producer(queue, WORK_COUNT * i, WORK_COUNT * (i + 1)));
        }
        for (int i = 0; i < CONSUMER_THREAD_COUNT; i++) {
            consumers.submit(new Consumer(queue));
        }
        try {
            producers.shutdown();
            LOGGER.info("waiting when producers is done");
            boolean terminated = producers.awaitTermination(3, TimeUnit.SECONDS);
            if (!terminated) {
                LOGGER.info("send interrupt flag to producers");
                producers.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities10ReentrantLock2", "main", e);
        }
        LOGGER.info("send interrupt flag to consumers");
        consumers.shutdownNow();
    }
}
