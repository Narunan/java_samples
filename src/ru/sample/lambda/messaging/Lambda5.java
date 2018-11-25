package ru.sample.lambda.messaging;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
@SuppressWarnings("unused")
class Lambda5 {

    //java.util.function.Predicate<T> — boolean test(T t)
    void printMessages(List<Message> list, Predicate<Message> predicate) {
        for (Message message : list) {
            if (predicate.test(message)) {
                System.out.println(message);
            }
        }
    }
}
