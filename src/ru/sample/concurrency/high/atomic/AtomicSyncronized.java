package ru.sample.concurrency.high.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nechaev Mikhail
 * Since 03/04/2017.
 */
class AtomicSyncronized {

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        while (!atomicInteger.compareAndSet(0, 1)) {
            //do
        }
        atomicInteger.set(0);
    }
}
