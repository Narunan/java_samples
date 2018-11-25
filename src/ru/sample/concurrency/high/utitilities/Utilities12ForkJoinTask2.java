package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities12ForkJoinTask2 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities12ForkJoinTask2.class.getSimpleName()
    );

    static class CustomTask extends RecursiveTask<Integer> {

        private final int threshold = 500;
        private final int[] numbers;
        private final int start;
        private final int end;

        private CustomTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if ((end - start) < threshold) {
                LOGGER.info("compute self: " + start + " : " + end);
                for (int i = start; i < end; i++) {
                    sum += numbers[i];
                }
            } else {
                LOGGER.info("divide: " + start + " : " + end);
                int middle = (start + end) / 2;
                CustomTask subTaskA = new CustomTask(numbers, start, middle);
                CustomTask subTaskB = new CustomTask(numbers, middle, end);

                subTaskA.fork();
                subTaskB.fork();
                sum = subTaskA.join() + subTaskB.join();
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        int[] numbers = new int[5000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }
        CustomTask task = new CustomTask(numbers, 0, numbers.length);
        int sum = forkJoinPool.invoke(task);
        int checkSum = 0;
        for (int i = 0; i < 5000; i++) {
            checkSum += i;
        }
        LOGGER.info("sum is " + sum + ", check sum is " + checkSum);
    }
}