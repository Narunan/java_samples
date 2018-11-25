package ru.sample.concurrency.low.threads;

import java.util.Arrays;

import ru.sample.concurrency.low.job.Jobs;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Thread5Join {

    private void run() {
        System.out.println("Hello!");
        Thread[] goodBoys = new Thread[3];
        Arrays.setAll(goodBoys, this::createGood);
        Arrays.stream(goodBoys).forEach(Thread::start);
        Arrays.stream(goodBoys).forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            }
        });
        System.out.println("Bye!");
    }

    private Thread createGood(int id) {
        return new Thread(
                () -> Jobs.doSimpleJob(10),
                "GoodBoy" + id
        );
    }

    public static void main(String[] args) {
        new Thread5Join().run();
    }
}
