package ru.mail.polis.lambda.messaging;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
interface CheckCondition {
    boolean check(Message message);
}
