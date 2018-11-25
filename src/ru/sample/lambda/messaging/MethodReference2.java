package ru.sample.lambda.messaging;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
@SuppressWarnings("unused")
class MethodReference2 {

    class Range {
        private final Timestamp from;
        private final Timestamp to;

        Range(Timestamp from, Timestamp to) {
            this.from = from;
            this.to = to;
        }

        boolean inRange(Message message) {
            Timestamp timestamp = message.getTimestamp();
            return from.before(timestamp) && timestamp.before(to);
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private void run(List<Message> list) {
        Range range = new Range(new Timestamp(123L), new Timestamp(987L));
        Set<User> set = new TreeSet<>();
        list.stream()
                .filter(range::inRange)
                .map(Message::getUser)
                .forEach(set::add);
    }
}
