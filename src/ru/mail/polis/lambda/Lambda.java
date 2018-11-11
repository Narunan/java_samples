package ru.mail.polis.lambda;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class Lambda {

    private static final Random RANDOM = new Random();

    @SuppressWarnings("unused")
    private Integer m(Function<String, Integer> function, String string) {
        return function.apply(string);
    }

    @SuppressWarnings("UnusedReturnValue")
    interface A<T> {
        boolean id(T t);
    }

    @SuppressWarnings("UnusedReturnValue")
    interface B {
        <T> boolean id(T t);
    }

    @SuppressWarnings("unused")
    private void run() {
        A<String> a = s -> true;
        //B b = s -> true;
        //B b = (String s) -> true;
        //B c = (String s) -> Objects.isNull(s);
        B b = Objects::isNull;

        List list = Arrays.asList("a", "b", "c", "d", "e");
        //Такое сделать нельзя. Два абстрактных метода (Не функциональный интерфейс)
        //Iterator<String> iterator = list::iterator;
        m(list::iterator);
        m((RANDOM.nextBoolean() ? list.subList(1, 2) : list)::iterator);
        D<Iterator<String>> lambda = (RANDOM.nextBoolean() ? list.subList(1, 2) : list)::iterator;
        lambda.get();
    }

    interface C<T> {
        Iterator<T> getIterator();
    }

    @SuppressWarnings("UnusedReturnValue")
    interface D<T> {
        T get();
    }

    @SuppressWarnings("unused")
    private void m(C<String> c) {
        Iterator<String> iterator = c.getIterator();
    }

    public static void main(String[] args) {
        new Lambda().run();
    }
}
