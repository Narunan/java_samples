package ru.sample.lambda.messaging;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Nechaev Mikhail
 * Since 22/04/2017.
 */
public class MessageGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] CITIES = new String[]{"Moscow", "Saint-Petersburg"};
    private static final long[] CHAT_IDX = new long[]{123456L, 10L, 123L};
    private static int messageId = 0;
    private static int userId = 0;

    static class TimestampConsumer implements Consumer<Timestamp> {

        final List<Message> list;

        TimestampConsumer() {
            list = new ArrayList<>();
        }

        @Override
        public void accept(Timestamp timestamp) {
            list.add(new Message(
                ++messageId,
                CHAT_IDX[RANDOM.nextInt(CHAT_IDX.length)],
                new User(
                        ++userId,
                        UUID.randomUUID().toString(),
                        CITIES[RANDOM.nextInt(CITIES.length)],
                        RANDOM.nextInt(100),
                        RANDOM.nextBoolean() ? Sex.MALE : Sex.FEMALE
                ),
                timestamp,
                UUID.randomUUID().toString()
            ));
        }

        void combine(TimestampConsumer other) {
            list.addAll(other.list);
        }

        List<Message> get() {
            return list;
        }
    }

    public static List<Message> generateList(int size) {
        return Stream.generate(System::currentTimeMillis)
                .distinct()
                .limit(size)
                .map(Timestamp::new)
                .collect(TimestampConsumer::new,
                        TimestampConsumer::accept,
                        TimestampConsumer::combine
                ).get();
    }
}
