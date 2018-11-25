package ru.sample.concurrency.high.utitilities;

import ru.sample.logger.LoggerUtils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 15/11/2018.
 */
class Utilities12ForkJoinTask1 {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            Utilities12ForkJoinTask1.class.getSimpleName()
    );

    private static class CustomAction extends RecursiveAction {

        private final int threshold = 500;
        private final int[] numbers;
        private final int start;
        private final int end;

        private CustomAction(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((end - start) < threshold) {
                LOGGER.info("compute self: " + start + " : " + end);
                for (int i = start; i < end; i++) {
                    numbers[i] += 10;
                }
            } else {
                LOGGER.info("divide: " + start + " : " + end);
                int middle = (start + end) / 2;
                CustomAction subTaskA = new CustomAction(numbers, start, middle);
                CustomAction subTaskB = new CustomAction(numbers, middle, end);
                invokeAll(subTaskA, subTaskB);
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int[] numbers = new int[5000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }
        CustomAction task = new CustomAction(numbers, 0, numbers.length);
        forkJoinPool.invoke(task);
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != i + 10) {
                throw new IllegalArgumentException("numbers must be incremented");
            }
        }
    }
}