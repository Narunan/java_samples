package ru.mail.polis.concurrency.low._synchronized;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Deadlock {

    class Contact {

        private final String name;

        Contact(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }

        synchronized void touch(Contact contact) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(this.name + " touch " + contact.getName());
            contact.unTouch(this);
        }

        synchronized void unTouch(Contact contact) {
            System.out.println(this.name + " unTouch " + contact.getName());
        }
    }

    private void run() {
        Contact first = new Contact("First");
        Contact second = new Contact("Second");
        Thread t1 = new Thread(() -> first.touch(second));
        Thread t2 = new Thread(() -> second.touch(first));
        trState(t1, t2);
        t1.start();
        t2.start();
        trState(t1, t2);
        sleep();
        trState(t1, t2);
        sleep();
        trState(t1, t2);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            /* empty */
        }
    }

    private void trState(Thread t1, Thread t2) {
        System.out.println("Thread 1: " + t1.getState());
        System.out.println("Thread 2: " + t2.getState());
    }

    public static void main(String[] args) {
        new Deadlock().run();
    }
}

/*
Full thread dump OpenJDK 64-Bit Server VM (11+28 mixed mode):

...

Found one Java-level deadlock:
=============================
"Thread-0":
  waiting to lock monitor 0x00007fb99503f900 (
        object 0x000000070fee9d10,
        a ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact
    ),
  which is held by "Thread-1"
"Thread-1":
  waiting to lock monitor 0x00007fb99503fa00 (
        object 0x000000070fee9cc8,
        a ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact
    ),
  which is held by "Thread-0"

Java stack information for the threads listed above:
===================================================
"Thread-0":
    at ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact.unTouch(Deadlock.java:32)
    - waiting to lock <0x000000070fee9d10> (a ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact)
    at ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact.touch(Deadlock.java:28)
    - locked <0x000000070fee9cc8> (a ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact)
    at ru.mail.polis.concurrency.low._synchronized.Deadlock.lambda$run$0(Deadlock.java:39)
    at ru.mail.polis.concurrency.low._synchronized.Deadlock$$Lambda$14/0x0000000800066840.run(Unknown Source)
    at java.lang.Thread.run(java.base@11/Thread.java:834)
"Thread-1":
    at ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact.unTouch(Deadlock.java:32)
    - waiting to lock <0x000000070fee9cc8> (a ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact)
    at ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact.touch(Deadlock.java:28)
    - locked <0x000000070fee9d10> (a ru.mail.polis.concurrency.low._synchronized.Deadlock$Contact)
    at ru.mail.polis.concurrency.low._synchronized.Deadlock.lambda$run$1(Deadlock.java:40)
    at ru.mail.polis.concurrency.low._synchronized.Deadlock$$Lambda$15/0x0000000800066c40.run(Unknown Source)
    at java.lang.Thread.run(java.base@11/Thread.java:834)

Found 1 deadlock.
 */