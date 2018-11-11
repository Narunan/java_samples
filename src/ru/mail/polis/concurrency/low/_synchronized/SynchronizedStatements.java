package ru.mail.polis.concurrency.low._synchronized;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 *
 * Аналогично SynchronizedCounter
 */
@SuppressWarnings("unused")
class SynchronizedStatements {

    private int c = 0;

    public void increment() {
        synchronized (this) {
            c++;
        }
    }

    public void decrement() {
        synchronized (this) {
            c--;
        }
    }

    public int value() {
        synchronized (this) {
            return c;
        }
    }
}
