package ru.mail.polis.concurrency.low.immutable;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Singleton {

    private static Singleton instance = null;

    private Singleton() {
        /* empty */
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
