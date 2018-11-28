package ru.sample.collections.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class Iterator5Split {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Spliterator<Integer> spliterator = list.spliterator();

        int characteristics = spliterator.characteristics();
        System.out.println("SIZED = " + ((characteristics & Spliterator.SIZED) != 0)); //true
        //or
        System.out.println(spliterator.hasCharacteristics(Spliterator.SIZED)); //true

        System.out.println(spliterator.getExactSizeIfKnown()); //10
        Spliterator<Integer> second = spliterator.trySplit();
        if (second != null) {
            System.out.println(spliterator.getExactSizeIfKnown()); //5
            System.out.println("forEachRemaining");
            spliterator.forEachRemaining(System.out::print); //56789
            System.out.println();
            System.out.println("--");
            System.out.println(second.getExactSizeIfKnown()); //5

            boolean success;
            success = second.tryAdvance((element) -> System.out.println("tryAdvanceFor: " + element)); //tryAdvanceFor: 0
            System.out.println("success = " + success); //true
            success = second.tryAdvance((element) -> System.out.println("tryAdvanceFor: " + element)); //tryAdvanceFor: 1
            System.out.println("success = " + success); //true
            second.forEachRemaining(System.out::print); //234
            System.out.println();
            success = second.tryAdvance((element) -> System.out.println("tryAdvanceFor: " + element));
            System.out.println("success = " + success); //false
        }
    }
}
