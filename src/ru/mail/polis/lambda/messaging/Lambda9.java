package ru.mail.polis.lambda.messaging;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Lambda9 {

    <T, R> void process(Iterable<T> elements, Predicate<T> predicate,
                        Function<T, R> mapper, Consumer<R> consumer) {
        //java.lang.Iterable<T> — Iterator<T> iterator()
        for (T element : elements) {
            //java.util.function.Predicate<T> — boolean test(T t)
            if (predicate.test(element)) {
                //java.util.function.Function<T, R> — R apply(T t)
                R data = mapper.apply(element);
                //java.util.function.Consumer<T> — void accept(T t)
                consumer.accept(data);
            }
        }
    }

    private void run(List<Message> list) {
        process(list,
                message -> 123L == message.getChatId(),
                message -> message.getUser().getName(),
                name -> System.out.println(name)
        );
        System.out.println();
        //Returns a sequential with this collection as its source.
        list.stream()
                //Stream<T> filter(Predicate<? super T> predicate);
                .filter(message -> 123L == message.getChatId())
                //<R> Stream<R> map(Function<? super T, ? extends R> mapper);
                .map(message -> message.getUser().getName())
                //void forEach(Consumer<? super T> action);
                .forEach(name -> System.out.println(name));
    }

    public static void main(String[] args) {
        new Lambda9().run(MessageGenerator.generateList(10));
    }
}
