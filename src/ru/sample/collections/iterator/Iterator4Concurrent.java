package ru.sample.collections.iterator;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class Iterator4Concurrent {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        System.out.println(list); //[A, B, C]
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " "); //A B C
        }
        System.out.println();
        iterator = list.iterator();
        list.add("D");
        try {
            if (iterator.hasNext()) {
                System.out.println("hasNext = true");
                System.out.println("next = " + iterator.next());
            }
        } catch (ConcurrentModificationException expected) {
            /*
                java.util.ConcurrentModificationException
                    at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1042)
                    at java.base/java.util.ArrayList$Itr.next(ArrayList.java:996)
                    at ru.sample.collections.iterator.Iterator4Concurrent.main(Iterator4Concurrent.java:27)
             */
            expected.printStackTrace();
        }
    }
}
