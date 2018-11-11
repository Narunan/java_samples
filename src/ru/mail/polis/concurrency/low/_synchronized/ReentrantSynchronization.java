package ru.mail.polis.concurrency.low._synchronized;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class ReentrantSynchronization {

    class Reentrant {

        synchronized void a() {
            System.out.println("A-1");
            b();
            System.out.println("A-2");
        }

        synchronized void b() {
            System.out.println("B-1");
            c();
            System.out.println("B-2");
        }

        synchronized void c() {
            System.out.println("C");
        }
    }

    private void run() {
        new Thread(() -> new Reentrant().a()).start();
    }

    public static void main(String[] args) {
        new ReentrantSynchronization().run();
    }
}
