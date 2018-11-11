package ru.mail.polis.lambda.messaging;

import java.util.List;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class Lambda4 {

    private void printMessages(List<Message> list,
                               CheckCondition checkCondition) {
        for (Message message : list) {
            if (checkCondition.check(message)) {
                System.out.println(message);
            }
        }
    }

    private void run(List<Message> list) {
        printMessages(list, message ->
                123456L == message.getChatId());
        System.out.println();
        printMessages(list, message ->
                "Saint-Petersburg".equals(message.getUser().getCity()));
    }

    public static void main(String[] args) {
        new Lambda4().run(MessageGenerator.generateList(10));
    }
}
