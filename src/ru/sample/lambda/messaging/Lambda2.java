package ru.sample.lambda.messaging;

import java.util.List;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class Lambda2 {

    class CheckConditionWithinChat implements CheckCondition {
        @Override
        public boolean check(Message message) {
            return 123456L == message.getChatId();
        }
    }

    class CheckConditionWithinCity implements CheckCondition {
        @Override
        public boolean check(Message message) {
            return "Saint-Petersburg".equals(message.getUser().getCity());
        }
    }

    private void printMessages(List<Message> list, CheckCondition checkCondition) {
        for (Message message : list) {
            if (checkCondition.check(message)) {
                System.out.println(message);
            }
        }
    }

    private void run(List<Message> list) {
        printMessages(list, new CheckConditionWithinChat());
        System.out.println();
        printMessages(list, new CheckConditionWithinCity());
    }

    public static void main(String[] args) {
        new Lambda2().run(MessageGenerator.generateList(10));
    }

}
