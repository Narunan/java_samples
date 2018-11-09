package ru.mail.polis.lambda.messaging;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Lambda1 {

    void printMessagesAfterTime(List<Message> list, Timestamp from) {
        for (Message message : list) {
            if (from.before(message.getTimestamp())) {
                System.out.println(message);
            }
        }
    }

    void printMessagesWithinRange(List<Message> list, Timestamp from, Timestamp to) {
        for (Message message : list) {
            Timestamp time = message.getTimestamp();
            if (from.before(time) && time.after(time)) {
                System.out.println(message);
            }
        }
    }

    void printMessagesWithinChat(List<Message> list, long chatId) {
        for (Message message : list) {
            if (chatId == message.getChatId()) {
                System.out.println(message);
            }
        }
    }

    void printMessagesWithinCity(List<Message> list, String city) {
        for (Message message : list) {
            if (city.equals(message.getUser().getCity())) {
                System.out.println(message);
            }
        }
    }

}
