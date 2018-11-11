package ru.mail.polis.concurrency.low._synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 *
 * Нельзя сделать synchronized конструктор,
 *  поэтому в многопоточной среде не стоит выкладывать в
 *  публичный доступ не до конца проинициализированные объекты
 */
class NonSynchronizedConstructor {

    class Clazz {

        final String usefulData;

        Clazz() {
            instances.add(this);
            doSomeLongImportantWorkForInitialization();
            usefulData = "42";
        }

        private void doSomeLongImportantWorkForInitialization() {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " thread is interrupted");
            }
        }

        /*
            Казалось бы всё хорошо.
            String проинициализируется в конструкторе и ОК
         */
        private int getSizeUsefulData() {
            return usefulData.length();
        }
    }

    private final List<Clazz> instances = new ArrayList<>();

    private void run() {
        Thread childTread = new Thread(Clazz::new);
        childTread.setDaemon(true);
        childTread.start();

        while (instances.isEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("main thread is interrupted");
            }
        }
        Clazz clazz = instances.get(0);
        /*
        Но нет.
            Объект до конца не проинициализировался, а ссылка на него уже есть
            Exception in thread "main" java.lang.NullPointerException
         */
        System.out.println(clazz.getSizeUsefulData());
    }

    public static void main(String[] args) {
        new NonSynchronizedConstructor().run();
    }
}
