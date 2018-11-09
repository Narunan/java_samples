package ru.mail.polis.concurrency.low.threads;

import ru.mail.polis.concurrency.low.job.Jobs;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class Thread4Interrupt {

    private Thread goodBoy = new Thread(() -> Jobs.doSimpleJob(10), "GoodBoy");
    private Thread badBoy = new Thread(() -> Jobs.doSimpleJobWorst(10), "BadBoy");

    private void run() {
        goodBoy.start();
        badBoy.start();
        checkHealt(0);
        badBoy.interrupt();
        checkHealt(1);
        badBoy.interrupt();
        checkHealt(2);
        checkHealt(3);
    }

    private void checkHealt(int step) {
        checkHealt(step, goodBoy);
        checkHealt(step, badBoy);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkHealt(int step, Thread thread) {
        System.out.printf(
                "Step=%d, info=%s. id=%d, isAlive=%b, isInterrupted=%b, currentState=%s%n",
                step,
                thread,
                thread.getId(),
                thread.isAlive(),
                thread.isInterrupted(),
                thread.getState()
        );
    }

    public static void main(String[] args) {
        new Thread4Interrupt().run();
    }
}
