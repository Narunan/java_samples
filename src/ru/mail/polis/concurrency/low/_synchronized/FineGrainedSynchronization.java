package ru.mail.polis.concurrency.low._synchronized;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class FineGrainedSynchronization {

    private int a = 0;
    private int b = 0;
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void incrementA() {
        synchronized (lockA) {
            a++;
        }
    }

    public void incrementB() {
        synchronized (lockB) {
            b--;
        }
    }
}
