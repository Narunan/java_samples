package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities4Exchanger {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities4Exchanger.class.getSimpleName(), Level.FINE
    );

    private static void doWork(Exchanger<Double> exchanger, int stage, PCConsumer pcConsumer) {
        Thread producer = new Thread(new Producer(exchanger), "producer-" + stage);
        Thread consumer = new Thread(new Consumer(exchanger),"consumer-" + stage);
        producer.start();
        consumer.start();
        pcConsumer.accept(producer, consumer);
        try {
            consumer.join();
            producer.join();
        } catch (InterruptedException e) {
            LOGGER.throwing("Utilities4Exchanger", "doWork", e);
        }
    }

    @FunctionalInterface
    interface PCConsumer {
        void accept(Thread producer, Thread consumer);
    }

    static class Producer implements Runnable {

        private final Exchanger<Double> exchanger;
        private final Random random;
        private boolean consumerIsAlive = true;

        Producer(Exchanger<Double> exchanger) {
            this.exchanger = exchanger;
            this.random = ThreadLocalRandom.current();
        }

        public void run() {
            LOGGER.info("start");
            int stages = random.nextInt(10);
            try {
                for (int i = 0; i < stages && !Thread.currentThread().isInterrupted(); i++) {
                    try {
                        double value = random.nextDouble();
                        LOGGER.info("Produce " + value);
                        exchanger.exchange(value, 1, TimeUnit.SECONDS);
                    } catch (TimeoutException e) {
                        consumerIsAlive = false;
                        LOGGER.throwing("Producer", "run", e);
                        return;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        LOGGER.throwing("Producer", "run", e);
                    }
                }
            } finally {
                if (consumerIsAlive) {
                    finish();
                }
                LOGGER.info("finish");
            }
        }

        private void finish() {
            try {
                exchanger.exchange(null, 1, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                LOGGER.info("not exchange in finish");
                LOGGER.throwing("Producer", "finish", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.throwing("Producer", "finish", e);
            }
        }
    }

    static class Consumer implements Runnable {
        private final Exchanger<Double> exchanger;
        private Double value;

        Consumer(Exchanger<Double> exchanger) {
            this.exchanger = exchanger;
        }

        public void run() {
            LOGGER.info("start");
            try {
                do {
                    try {
                        value = exchanger.exchange(null, 1, TimeUnit.SECONDS);
                        LOGGER.info("Consume " + value);
                    } catch (TimeoutException e) {
                        LOGGER.throwing("Consumer", "run", e);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        LOGGER.throwing("Consumer", "run", e);
                    }
                } while (!Thread.currentThread().isInterrupted() && value != null);
            } finally {
                finish();
            }
        }

        private void finish() {
            LOGGER.info("finish");
        }
    }

    public static void main(String[] args) {
        Exchanger<Double> exchanger = new Exchanger<>();

        doWork(exchanger, 1, ((producer, consumer) -> { /* nothing */ }));
        doWork(exchanger, 2, ((producer, consumer) -> producer.interrupt()));
        doWork(exchanger, 3, ((producer, consumer) -> consumer.interrupt()));
    }
}