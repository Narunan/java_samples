package ru.sample.concurrency.low.job;

import java.util.concurrent.TimeUnit;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class Jobs {

    public static void doSimpleJob(int count) {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello, " + threadName);
        try {
            for (int i = 0; i < count; i++) {
                System.out.println(threadName + " unloaded a wagon of coal #" + i);
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
        } catch (InterruptedException e1) {
            System.err.println("Relax, " + threadName);
        } finally {
            System.out.println("Good job, " + threadName);
        }
    }

    public static void doSimpleJobWorst(int count) {
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.getName();
        System.out.println("Hello, " + threadName);
        int amountOfDoingNothing = 0;
        while (true) {
            amountOfDoingNothing++;
            if (currentThread.isInterrupted()) {
                System.out.println("<" + threadName + "> amount of doing nothing = " + amountOfDoingNothing);
                break;
            }
        }
        try {
            for (int i = 0; i < count; i++) {
                Thread.sleep(
                        TimeUnit.SECONDS.toMillis(2),
                        9999
                );
                System.out.println(threadName + " unloaded a wagon of coal #" + i);
            }
            System.out.println("Good job, " + threadName);
        } catch (InterruptedException e1) {
            System.out.println("<" + threadName + "> is interrupted = " + currentThread.isInterrupted());
            System.err.println("You're fired, " + threadName);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(4));
            } catch (InterruptedException e2) {
                System.out.println("<" + threadName + "> is interrupted = " + currentThread.isInterrupted());
                System.err.println("Go away, " + threadName + "!");
            }
        }
    }
}
