package ru.sample.anonymous;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nechaev Mikhail
 * Since 15/04/2017.
 */
class AnonymousClass1 {

    interface AlarmClock {

        void start(Date date);

        void stop();
    }

    private void run() {
        AlarmClock singleAlarmClock = new AlarmClock() {

            private final Timer timer = new Timer();
            private TimerTask awakeTask = null;

            @Override
            public void start(Date date) throws IllegalArgumentException {
                if (date.before(new Date())) {
                    throw new IllegalArgumentException("Time in past");
                }
                if (awakeTask != null) {
                    awakeTask.cancel();
                }
                awakeTask = new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Single WakeUp!");
                    }
                };
                timer.schedule(awakeTask, date);
            }

            @Override
            public void stop() {
                awakeTask.cancel();
            }
        };

        class MultiAlarmClock implements AlarmClock {

            private final Timer timer = new Timer();
            private final List<TimerTask> awakeTasks = new ArrayList<>();

            @Override
            public void start(Date date) throws IllegalArgumentException {
                if (date.before(new Date())) {
                    throw new IllegalArgumentException("Time in past");
                }
                TimerTask awakeTask = new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Multi WakeUp!");
                    }
                };
                awakeTasks.add(awakeTask);
                timer.schedule(awakeTask, date);
            }

            @Override
            public void stop() {
                awakeTasks.forEach(TimerTask::cancel);
                awakeTasks.clear();
                timer.purge();
            }
        }
        long now = System.currentTimeMillis();
        singleAlarmClock.start(new Date(now + TimeUnit.SECONDS.toMillis(5)));
        AlarmClock multiAlarmClock = new MultiAlarmClock();
        multiAlarmClock.start(new Date(now + TimeUnit.SECONDS.toMillis(5)));
        multiAlarmClock.start(new Date(now + TimeUnit.SECONDS.toMillis(10)));
        multiAlarmClock.start(new Date(now + TimeUnit.SECONDS.toMillis(15)));
        multiAlarmClock.stop();
        multiAlarmClock.start(new Date(now + TimeUnit.SECONDS.toMillis(1)));
        multiAlarmClock.start(new Date(now + TimeUnit.SECONDS.toMillis(2)));
    }

    public static void main(String[] args) {
        new AnonymousClass1().run();
    }
}
