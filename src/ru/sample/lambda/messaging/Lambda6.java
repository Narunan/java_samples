package ru.sample.lambda.messaging;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class Lambda6 {

    private void processMessages(List<Message> list,
                                 Predicate<Message> predicate,
                                 Consumer<Message> consumer) {
        for (Message message : list) {
            //java.util.function.Predicate<T> — boolean test(T t)
            if (predicate.test(message)) {
                //java.util.function.Consumer<T> — void accept(T t)
                consumer.accept(message);
            }
        }
    }

    @SuppressWarnings("Convert2MethodRef")
    private void run(List<Message> list) {
        Timestamp timestamp = Timestamp.from(
                Instant.now().minus(5, ChronoUnit.DAYS)
        );
        long chatId = 10L;
        processMessages(list,
                message -> timestamp.before(message.getTimestamp()) && chatId == message.getChatId(),
                message -> System.out.println(message)
        );
    }

    public static void main(String[] args) {
        new Lambda6().run(MessageGenerator.generateList(10));
    }
}
