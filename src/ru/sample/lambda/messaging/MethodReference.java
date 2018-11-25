package ru.sample.lambda.messaging;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
@SuppressWarnings("unused")
class MethodReference {

    class ComparatorByTimestamp implements Comparator<Message> {
        @Override
        public int compare(Message first, Message second) {
            return Message.compareByTimestamp(first, second);
        }
    }

    @SuppressWarnings({"Convert2Diamond", "Convert2Lambda", "Anonymous2MethodRef", "Convert2MethodRef"})
    private void run(List<Message> list) {
        list.sort(new ComparatorByTimestamp());
        list.sort(new Comparator<Message>() {
            @Override
            public int compare(Message first, Message second) {
                return Message.compareByTimestamp(first, second);
            }
        });
        list.sort((first, second) -> Message.compareByTimestamp(first, second));
        list.sort(Message::compareByTimestamp);
    }

}
