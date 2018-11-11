package ru.mail.polis.concurrency.low.immutable;

import java.util.concurrent.TimeUnit;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
@SuppressWarnings("SameParameterValue")
class RagInterval {

    private String name;
    private int left;
    private int right;

    private RagInterval(String name, int left, int right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    private synchronized void update(String name, int left, int right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    private synchronized String getName() {
        return name;
    }

    private synchronized boolean inside(int coordinate) {
        return left <= coordinate && coordinate <= right;
    }

    public static void main(String[] args) {
        RagInterval ragInterval = new RagInterval("First", 1, 2);
        Thread thread1 = new Thread(() -> {
            String name = ragInterval.getName();
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                System.out.println("thread1 is interrupted");
            }
            //expected: false
            //actual: true
            System.out.println("in " + name + " 5 inside [1:2] = " + ragInterval.inside(5));
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                System.out.println("thread1 is interrupted");
            }
            ragInterval.update("Second", 1, 10);
        });
        thread1.start();
        thread2.start();
    }
}
