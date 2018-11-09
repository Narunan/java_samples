package ru.mail.polis.lambda;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Lambda {

    private Integer m(Function<String, Integer> function, String string) {
        return function.apply(string);
    }

    interface A<T> {
        boolean id(T t);
    }
    interface B {
        <T> boolean id(T t);
    }

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
        m((true ? list.subList(1, 2) : list)::iterator);
        D<Iterator<String>> lambda = (true ? list.subList(1, 2) : list)::iterator;
        lambda.get();
    }

    interface C<T> {
        Iterator<T> getIterator();
    }
    interface D<T> {
        T get();
    }

    private void m(C<String> c) {
        Iterator<String> iterator = c.getIterator();
    }

    public static void main(String[] args) {
        new Lambda().run();
    }
}
