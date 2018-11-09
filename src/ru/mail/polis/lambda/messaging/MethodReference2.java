package ru.mail.polis.lambda.messaging;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class MethodReference2 {

    class Range {
        private Timestamp from;
        private Timestamp to;

        public Range(Timestamp from, Timestamp to) {
            this.from = from;
            this.to = to;
        }

        boolean inRange(Message message) {
            Timestamp timestamp = message.getTimestamp();
            return from.before(timestamp) && timestamp.before(to);
        }
    }

    private void run(List<Message> list) {
        Range range = new Range(new Timestamp(123L), new Timestamp(987L));
        Set<User> set = new TreeSet<>();
        list.stream()
                .filter(range::inRange)
                .map(Message::getUser)
                .forEach(set::add);
        list.stream()
                .filter(range::inRange)
                .map(Message::getUser)
                .forEach(set::add);
    }
}
