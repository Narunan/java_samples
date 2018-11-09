package ru.mail.polis.concurrency.low._synchronized.guarded;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class WaitNotify {
    private static final Object lock = new Object();
    private static int data = 0;

    public void set(int value) {
        synchronized (lock) {
            data = value;
            lock.notifyAll();
        }
    }

    public int get() {
        synchronized (lock) {
            while (data != 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    /* empty */
                }
            }
            int value = data;
            data = 0;
            return value;
        }
    }
}
