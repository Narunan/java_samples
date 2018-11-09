package ru.mail.polis.stream;

import java.util.stream.Stream;

/**
 * Created by Nechaev Mikhail
 * Since 17/04/2017.
 */
public class Stream2 {

    public static void main(String[] args) {
        Stream empty = Stream.empty();
        Stream numbers = Stream.of(1, 2, 3, 4);
        Stream strings = Stream.of("A", "B", "C", "D");
        //1234ABCD
        Stream.concat(numbers, strings).forEach(System.out::print);
        System.out.println();
        Stream builder = Stream.builder()
                .add(1).add(2).add(3)
                .build();
        //1492377352682 1492377352683 1492377352684
        //4477132058049
        long sum = Stream.generate(System::currentTimeMillis)
                .distinct()
                .limit(3)
                .peek(System.out::println)
                .mapToLong(a -> a)
                .sum();
        System.out.println(sum);
        //AAAAAA AAAAAA AAAAAAAA
        Stream.iterate("A", (seed) -> seed + "A")
                .skip(5).limit(3)
                .forEach(System.out::println);
    }
}
