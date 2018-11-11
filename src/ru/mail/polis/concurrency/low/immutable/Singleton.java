package ru.mail.polis.concurrency.low.immutable;

import java.util.Arrays;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Singleton {

    private static Singleton instance = null;

    private Singleton() {
        /* empty */
    }

    private void print() {
        System.out.println(Thread.currentThread().getName() + " in print");
    }

    private static synchronized Singleton getInstance() {
        if (instance == null) {
            System.out.println(Thread.currentThread().getName() + " create new instance");
            instance = new Singleton();
        } else {
            System.out.println(Thread.currentThread().getName() + " use existing instance");
        }
        return instance;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            /*
             * Если тут написать Singleton.getInstance()::print,
             *   то getInstance() будет вызываться из main thread
             */
            //noinspection CodeBlock2Expr
            threads[i] = new Thread(() -> {
                Singleton.getInstance().print();
            });
        }
        Arrays.stream(threads).forEach(Thread::start);
    }
}
