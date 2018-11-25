package ru.sample.lambda.messaging;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class Lambda8 {

    private <T, R> void process(Iterable<T> elements,
                                Predicate<T> predicate,
                                Function<T, R> mapper,
                                Consumer<R> consumer) {
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

    @SuppressWarnings("Convert2MethodRef")
    private void run(List<Message> list) {
        process(list,
                message -> 123L == message.getChatId(),
                message -> message.getUser().getName(),
                name -> System.out.println(name)
        );
        System.out.println();
        process(list,
                message -> message.getData().contains("abc"),
                message -> message.getData(),
                s -> System.out.println(s)
        );
    }

    public static void main(String[] args) {
        new Lambda8().run(MessageGenerator.generateList(10));
    }
}
