package ru.sample.collections.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class Iterator3List2 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        System.out.println(list); //[]
        ListIterator<Integer> listIterator = list.listIterator();
        for (int i = 0; i < 10; i++) {
            listIterator.add(i); //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        }
        System.out.println(list);
        while (listIterator.hasPrevious()) {
            int prev = listIterator.previous();
            if (prev % 5 == 0) {
                listIterator.set(prev + 1000); //[1000, 1, 2, 3, 4, 1005, 6, 7, 8, 9]
            }
        }
        System.out.println(list);
        while (listIterator.hasNext()) {
            int next = listIterator.next();
            if (next % 2 == 0) {
                listIterator.remove();
            }
        }
        System.out.println(list); //[1, 3, 1005, 7, 9]
    }
}
