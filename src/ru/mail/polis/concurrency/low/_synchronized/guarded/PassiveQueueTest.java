package ru.mail.polis.concurrency.low._synchronized.guarded;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class PassiveQueueTest {

    public static void main(String[] args) {
        QueuePassive queue = new QueuePassive();
        AtomicInteger count = new AtomicInteger(0);
        int maxSetters = 2;
        int maxGetters = 2;
        Thread[] setters = new Thread[maxSetters];
        Thread[] getters = new Thread[maxGetters];
        for (int i = 0; i < maxSetters; i++) {
            final int left = 10 * i;
            final int right = 10 + left;
            setters[i] = new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Setter " + threadName);
                int current = left;
                while (current < right) {
                    try {
                        String data = threadName + " : " + current;
                        queue.setData(data);
                        current++;
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted in set " + threadName);
                    }
                }
            });
        }
        for (int i = 0; i < maxGetters; i++) {
            getters[i] = new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Getter " + threadName);
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String data = threadName + " -- " + queue.getData();
                        System.out.println(data);
                        count.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted in get " + threadName);
                }
            });
        }
        Arrays.stream(setters).forEach(Thread::start);
        Arrays.stream(getters).forEach(Thread::start);
        Arrays.stream(setters).forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ie) {
                System.out.println("Interrupted in join " + Thread.currentThread().getName());
            }
        });
        Arrays.stream(getters).forEach(Thread::interrupt);
        System.out.println(count.get() + " == " + 10 * maxSetters);
    }
}
