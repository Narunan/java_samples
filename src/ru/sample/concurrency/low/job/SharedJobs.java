package ru.sample.concurrency.low.job;

import java.util.concurrent.TimeUnit;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class SharedJobs {

    private int wagonId = 0;

    public void doSharedSimpleJob(int count) {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello, " + threadName);
        try {
            for (int i = 0; i < count; i++) {
                System.out.println(threadName + " unloaded a wagon of coal #" + ++wagonId);
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
        } catch (InterruptedException e1) {
            System.err.println("Relax, " + threadName);
        } finally {
            System.out.println("Good job, " + threadName);
        }
    }

    public void doSharedSimpleJobSeparately(int count) {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello, " + threadName);
        try {
            for (int i = 0; i < count; i++) {
                System.out.println(threadName + " unloaded a wagon of coal #" + getNextWagonId());
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
        } catch (InterruptedException e1) {
            System.err.println("Relax, " + threadName);
        } finally {
            System.out.println("Good job, " + threadName);
        }
    }

    private synchronized int getNextWagonId() {
        return ++wagonId;
    }
}
