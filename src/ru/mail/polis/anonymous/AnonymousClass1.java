package ru.mail.polis.anonymous;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Created by Nechaev Mikhail
 * Since 15/04/2017.
 */
public class AnonymousClass1 {
    interface AlarmClock {
        void start(LocalDateTime dateTime);
        void stop();
    }
    private void run() {
        AlarmClock singleAlarmClock = new AlarmClock() {
            private LocalDateTime awakeTime;
            @Override
            public void start(LocalDateTime dateTime)
                    throws IllegalArgumentException {
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(dateTime)) {
                    throw new IllegalArgumentException(
                            now + " " + dateTime
                    );
                }
                this.awakeTime = dateTime;
                schedule();
            }
            @Override
            public void stop() {
                this.awakeTime = null;
                cancel();
            }
            private void schedule() {
                System.out.println("scheduled single");
            }
            private void cancel() {
                System.out.println("canceled single");
            }
        };
        class MultiAlarmClock implements AlarmClock {
            private NavigableSet<LocalDateTime> awakeTimes;
            @Override
            public void start(LocalDateTime dateTime)
                    throws IllegalArgumentException {
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(dateTime)) {
                    throw new IllegalArgumentException(
                            now + " " + dateTime
                    );
                }
                if (Objects.isNull(awakeTimes)) {
                    awakeTimes = new TreeSet<>();
                }
                awakeTimes.add(dateTime);
                schedule();
            }
            @Override
            public void stop() {
                awakeTimes.clear();
                cancel();
            }
            private void schedule() {
                System.out.println("scheduled multi");
            }
            private void cancel() {
                System.out.println("canceled multi");
            }
        }
        AlarmClock multiAlarmClock = new MultiAlarmClock();
        singleAlarmClock.start(LocalDateTime.now().plusHours(2));
        multiAlarmClock.start(LocalDateTime.now().plus(4, ChronoUnit.DAYS));
    }
    public static void main(String[] args) {
        new AnonymousClass1().run();
    }
}
