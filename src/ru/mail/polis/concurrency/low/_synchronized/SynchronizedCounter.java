package ru.mail.polis.concurrency.low._synchronized;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class SynchronizedCounter {

    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}
