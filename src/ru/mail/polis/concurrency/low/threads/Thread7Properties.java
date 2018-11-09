package ru.mail.polis.concurrency.low.threads;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class Thread7Properties {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {});
        thread.setName("name");
        thread.setDaemon(true);
        thread.setPriority(7);
        //После start свойства изменить нельзя
        thread.start();
        System.out.println(thread.isAlive()); //boolean
        System.out.println(thread.isInterrupted()); //boolean
        System.out.println(thread.isDaemon()); //boolean
        System.out.println(thread.getName()); //String
        System.out.println(thread.getId()); //long
        //MIN_PRIORITY = 1
        //NORM_PRIORITY = 5
        //MAX_PRIORITY = 10
        System.out.println(thread.getPriority()); //int
        //NEW,RUNNABLE,BLOCKED,WAITING,TIMED_WAITING,TERMINATED
        System.out.println(thread.getState()); //State
    }
}
