package ru.sample.concurrency.low.threads;

import ru.sample.concurrency.low.job.Jobs;

import java.util.Arrays;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Thread6Daemon {

    private void run() {
        System.out.println("Hello!");
        Thread[] goodBoys = new Thread[3];
        Arrays.setAll(goodBoys, this::createGood);
        Arrays.stream(goodBoys).forEach(Thread::start);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("I'm bankrupt!");
    }

    private Thread createGood(int id) {
        Thread thread = new Thread(
                () -> Jobs.doSimpleJob(10),
                "UnLuckBoy" + id
        );
        thread.setDaemon(true);
        return thread;
    }

    public static void main(String[] args) {
        new Thread6Daemon().run();
    }
}