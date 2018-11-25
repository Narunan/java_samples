package ru.sample.concurrency.low.threads;

import ru.sample.concurrency.low.job.Jobs;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class Thread3Extend {

    public static void main(String[] args) {
        new Thread3Extend().r();
    }

    private void r() {
        Thread firstChild = new Thread3ExtendInner();
        Thread secondChild = new Thread3ExtendInner("SecondChild");
        Thread3ExtendInner thirdChild = new Thread3ExtendInner("ThirdChild");
        firstChild.start();
        secondChild.start();
        thirdChild.start();

        thirdChild.customMethod();

        Jobs.doSimpleJob(5);
    }

    class Thread3ExtendInner extends Thread {

        Thread3ExtendInner() {
            super();
        }

        Thread3ExtendInner(String name) {
            super(name);
        }

        @Override
        public void run() {
            Jobs.doSimpleJob(5);
        }

        void customMethod() {
            System.out.println("customMethod");
        }


    }
}
