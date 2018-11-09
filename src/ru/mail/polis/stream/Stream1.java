package ru.mail.polis.stream;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.mail.polis.lambda.messaging.Message;
import ru.mail.polis.lambda.messaging.MessageGenerator;
import ru.mail.polis.lambda.messaging.User;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Stream1 {

    private void run(List<Message> messages) {
        Timestamp timestamp = Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS));
        Map<Long, Map<User, Long>> result = messages.stream()
                .filter((message) -> timestamp.before(message.getTimestamp()))
                .collect(Collectors.groupingBy(
                        Message::getChatId,
                        Collectors.groupingBy(
                                Message::getUser,
                                Collectors.counting()
                        )
                    )
                );
        for (Map.Entry<Long, Map<User, Long>> entry : result.entrySet()) {
            System.out.println("--" + entry.getKey() + "--");
            for (Map.Entry<User, Long> valueEntry : entry.getValue().entrySet()) {
                System.out.println("--" + valueEntry.getKey() + " == " + valueEntry.getValue());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Stream1().run(MessageGenerator.generateList(10));
    }
}
