package ru.mail.polis.lambda.messaging;

import java.sql.Timestamp;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Message {
    private final long id;
    private final long chatId;
    private final User user;
    private final Timestamp timestamp;
    private final String data;

    public Message(long id, long chatId, User user, Timestamp timestamp, String data) {
        this.id = id;
        this.chatId = chatId;
        this.user = user;
        this.timestamp = timestamp;
        this.data = data;
    }

    @SuppressWarnings("unused")
    public long getId() { return id; }

    public long getChatId() { return chatId; }

    public User getUser() { return user; }

    public Timestamp getTimestamp() { return timestamp; }

    public String getData() { return data; }

    public static int compareByTimestamp(Message first, Message second) {
        return first.getTimestamp().compareTo(second.getTimestamp());
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", user=" + user +
                ", timestamp=" + timestamp +
                ", data='" + data + '\'' +
                '}';
    }
}
