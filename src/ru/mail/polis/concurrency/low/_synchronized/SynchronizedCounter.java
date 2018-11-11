package ru.mail.polis.concurrency.low._synchronized;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class SynchronizedCounter {

    private static class Sync {

        private int c = 0;

        private synchronized void increment() {
            c++;
        }

        private synchronized void decrement() {
            c--;
        }

        private synchronized int value() {
            return c;
        }
    }

    private static class UnSync {

        private int c = 0;

        private void increment() {
            c++;
        }

        private void decrement() {
            c--;
        }

        private int value() {
            return c;
        }
    }

    private static class CounterAction implements Runnable {

        private final Runnable unSyncRunnable;
        private final Runnable syncRunnable;

        CounterAction(Runnable unSyncRunnable, Runnable syncRunnable) {
            this.unSyncRunnable = unSyncRunnable;
            this.syncRunnable = syncRunnable;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100 && !Thread.currentThread().isInterrupted(); i++) {
                unSyncRunnable.run();
                syncRunnable.run();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UnSync unSync = new UnSync();
        Sync sync = new Sync();
        Thread[] threads = new Thread[21];
        for (int i = 0; i < 21; i++) {
            boolean doIncrement = i % 2 == 0;
            threads[i] = new Thread(new CounterAction(
                    doIncrement ? unSync::increment : unSync::decrement,
                    doIncrement ? sync::increment : sync::decrement
            ));
        }
        for (int i = 0; i < 21; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 21; i++) {
            threads[i].join();
        }
        //false
        System.out.println(
                "unSyncValue = " + unSync.value() + "."
                + " 100 == unSync.value() is " + (100 == unSync.value())
        );
        //true
        System.out.println(100 == sync.value());
    }
}
