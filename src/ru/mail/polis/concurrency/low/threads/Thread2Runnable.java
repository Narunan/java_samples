package ru.mail.polis.concurrency.low.threads;

import ru.mail.polis.concurrency.low.job.Jobs;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Thread2Runnable {

    public static void main(String[] args) {
        new Thread2Runnable().r();
    }

    private void r() {
        Thread childThread = new Thread(new Thread2RunnableInner(), "ChildThread");
        childThread.start();

        Jobs.doSimpleJob(5);
    }

    class Thread2RunnableInner implements Runnable {

        @Override
        public void run() {
            Jobs.doSimpleJob(5);
        }
    }
}