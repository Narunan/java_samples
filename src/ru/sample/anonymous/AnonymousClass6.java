package ru.sample.anonymous;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class AnonymousClass6 {
    private void methodReference() {
        System.out.println("methodReference.run");
    }

    @SuppressWarnings({"Convert2Lambda", "CodeBlock2Expr"})
    private void run() {
        Executor executor = Executors.newCachedThreadPool();
        //void execute(Runnable command);
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("localClass.run");
            }
        };
        executor.execute(myRunnable);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("anonymousClass.run");
            }
        });
        executor.execute(() -> {
            System.out.println("lambdaExpression.run");
        });
        executor.execute(this::methodReference);
    }
    public static void main(String[] args) {
        new AnonymousClass6().run();
    }
}
