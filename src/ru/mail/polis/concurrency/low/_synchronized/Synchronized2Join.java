package ru.mail.polis.concurrency.low._synchronized;

import java.util.Arrays;
import java.util.stream.Stream;

import ru.mail.polis.concurrency.low.job.SharedJobs;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
public class Synchronized2Join {

    private SharedJobs sharedJobs = new SharedJobs();

    private void run() {
        System.out.println("Hello!");
        Thread[] goodBoys = new Thread[3];
        Arrays.setAll(goodBoys, (index) ->
                new Thread(() -> sharedJobs.doSharedSimpleJobSeparately(10), "LonelyBoy" + index)
        );
        Arrays.stream(goodBoys).forEach(Thread::start);
        Stream<Thread> threadStream = Arrays.stream(goodBoys);
        threadStream.forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            }
        });
        System.out.println("Bye!");
    }

    public static void main(String[] args) {
        new Synchronized2Join().run();
    }
}
