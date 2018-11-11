package ru.mail.polis.lambda.messaging;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class Lambda7 {

    private void processMessages(List<Message> list,
                                 Predicate<Message> predicate,
                                 Function<Message, String> mapper,
                                 Consumer<String> consumer) {
        for (Message message : list) {
            //java.util.function.Predicate<T> — boolean test(T t)
            if (predicate.test(message)) {
                //java.util.function.Function<T, R> — R apply(T t)
                String data = mapper.apply(message);
                //java.util.function.Consumer<T> — void accept(T t)
                consumer.accept(data);
            }
        }
    }

    @SuppressWarnings("Convert2MethodRef")
    private void run(List<Message> list) {
        processMessages(list,
                message -> 123L == message.getChatId(),
                message -> message.getUser().getName(),
                name -> System.out.println(name)
        );
    }

    public static void main(String[] args) {
        new Lambda7().run(MessageGenerator.generateList(10));
    }
}
