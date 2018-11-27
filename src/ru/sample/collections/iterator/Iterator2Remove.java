package ru.sample.collections.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class Iterator2Remove {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>( //wrap and make it mutable
                Arrays.asList("A", "B", "C", "D") //immutable list
        );
        System.out.println(list); //[A, B, C, D]
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if ("C".equals(next)) {
                iterator.remove();
            }
        }
        System.out.println(list); //[A, B, D]
        list.removeIf("D"::equals);
        System.out.println(list); //[A, B]
    }
}
