package ru.mail.polis.lambda.messaging;

import java.util.function.Consumer;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Lambda10 {
    static int a = 1; //member enclosing class
    int b = 2; //member enclosing class
    private void run(
            int b /*local scope: effective final. Java 8 */, int d) {
        d--; //local scope: NON effective final
        int e = 5; e++; //local scope: NON effective final
        final int f = 6; //local scope: final
        int g = 7; //local scope: effective final
        //Скоуп ограничен лексически
        Consumer<Integer> consumer = (a/*нельзя b,d,e,f,g*/) -> {
            System.out.println(
                    a + Lambda10.a + b + this.b/*outer*/
                    + f + g //+ d + e
            );
            //int g = 10; no shadowing
            System.out.println(g);
        };
        consumer.accept(d);
    }
    public static void main(String[] args) {
        new Lambda10().run(3, 4);
    }
}
