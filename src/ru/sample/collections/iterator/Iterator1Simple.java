package ru.sample.collections.iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class Iterator1Simple {

    private static final Consumer<String> CONSUMER = System.out::print;

    public static void main(String[] args) {
        Collection<String> collection = Set.of("A", "B", "C", "D");
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            CONSUMER.accept(iterator.next());
        }
        System.out.println();
        System.out.println("--");
        iterator = collection.iterator();
        iterator.forEachRemaining(CONSUMER); //invoke for every element
        System.out.println();
        System.out.println("false == " + iterator.hasNext());


    }
}
