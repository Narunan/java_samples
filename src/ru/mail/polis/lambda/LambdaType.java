package ru.mail.polis.lambda;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class LambdaType {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        //Future<?> submit(Runnable task);
        //public abstract void run();
        executorService.submit(() -> {});
        //<T> Future<T> submit(Callable<T> task);
        //V call() throws Exception;
        executorService.submit(() -> 42);
    }
}
