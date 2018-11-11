package ru.mail.polis.lambda.messaging;

import java.util.List;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class Lambda3 {

    private void printMessages(List<Message> list, CheckCondition checkCondition) {
        for (Message message : list) {
            if (checkCondition.check(message)) {
                System.out.println(message);
            }
        }
    }

    @SuppressWarnings("Convert2Lambda")
    private void run(List<Message> list) {
        printMessages(list, new CheckCondition() {
            @Override
            public boolean check(Message message) {
                return 123456L == message.getChatId();
            }
        });
        System.out.println();
        printMessages(list, new CheckCondition() {
            @Override
            public boolean check(Message message) {
                return "Saint-Petersburg".equals(message.getUser().getCity());
            }
        });
    }

    public static void main(String[] args) {
        new Lambda3().run(MessageGenerator.generateList(10));
    }
}
