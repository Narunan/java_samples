package ru.mail.polis.stream;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Nechaev Mikhail
 * Since 17/04/2017.
 */
public class Stream3 {
    public static void main(String[] args) {
        int[] array = IntStream.range(0, 10).toArray();
        System.out.println(Arrays.toString(array));

        int sum = IntStream.range(0, 10).reduce((left, right) -> left + right).getAsInt();
        int hash = "TechnoPolis".chars().reduce(0, (a, b) -> a * 31 + b);
        //45
        System.out.println(sum);
        //1122517888 == 1122517888
        System.out.println("TechnoPolis".hashCode() + " == " + hash);
    }
}
