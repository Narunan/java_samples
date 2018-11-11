package ru.mail.polis.stream;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nechaev Mikhail
 * Since 17/04/2017.
 */
class Stream5 {

    public static void main(String[] args) {
        Map<String, String> map = Stream
                .of("A:1", "B:2", "C:3", "D:4")
                .filter((value) -> value.contains(":"))
                .map((value) -> value.split(":"))
                .collect(Collectors.toMap(
                        (value) -> value[0],
                        (value) -> value[1]
                ));
        //{A=1, B=2, C=3, D=4}
        System.out.println(map);
    }
}
