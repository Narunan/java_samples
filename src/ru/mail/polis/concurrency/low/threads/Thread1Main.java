package ru.mail.polis.concurrency.low.threads;

import ru.mail.polis.concurrency.low.job.Jobs;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Thread1Main {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread.getName());
        mainThread.setName("MainThread");
        System.out.println(mainThread.getName());

        Jobs.doSimpleJob(10);
    }
}
