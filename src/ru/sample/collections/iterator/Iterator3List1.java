package ru.sample.collections.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class Iterator3List1 {

    private static void forward(ListIterator<String> listIterator) {
        System.out.println("forward");
        while (listIterator.hasNext()) {
            /*
                pi=-1	ni=0	n=A	pi=0	ni=1
                pi=0	ni=1	n=B	pi=1	ni=2
                pi=1	ni=2	n=C	pi=2	ni=3
                pi=2	ni=3	n=D	pi=3	ni=4
             */
            System.out.println(
                    "pi=" + listIterator.previousIndex()
                            + "\tni=" + listIterator.nextIndex()
                            + "\tn=" + listIterator.next()
                            + "\tpi=" + listIterator.previousIndex()
                            + "\tni=" + listIterator.nextIndex()
            );
        }
    }

    private static void backward(ListIterator<String> listIterator) {
        System.out.println("backward");
        while (listIterator.hasPrevious()) {
            /*
                pi=3	ni=4	p=D	pi=2	ni=3
                pi=2	ni=3	p=C	pi=1	ni=2
                pi=1	ni=2	p=B	pi=0	ni=1
                pi=0	ni=1	p=A	pi=-1	ni=0
             */
            System.out.println(
                    "pi=" + listIterator.previousIndex()
                            + "\tni=" + listIterator.nextIndex()
                            + "\tp=" + listIterator.previous()
                            + "\tpi=" + listIterator.previousIndex()
                            + "\tni=" + listIterator.nextIndex()
            );
        }
    }

    private static void log(String prefix, ListIterator<String> listIterator) {
        System.out.println(prefix + ": pi=" + listIterator.previousIndex() + ", ni=" + listIterator.nextIndex());
    }

    private static void add(List<String> list) {
        System.out.println("add");
        ListIterator<String> listIterator = list.listIterator();
        System.out.println(list); //[A, B, C, D]
        while (listIterator.hasNext()) {
            if (2 == listIterator.nextIndex()) {
                log("before add", listIterator); //before add: pi=1, ni=2
                listIterator.add("2"); //+ implicit next
                log("after add", listIterator); //after add: pi=2, ni=3
                break;
            } else {
                listIterator.next();
            }
        }
        System.out.println(list); //[A, B, 2, C, D]
    }

    private static void set(List<String> list) {
        System.out.println("set");
        ListIterator<String> listIterator = list.listIterator();
        System.out.println(list); //[A, B, 2, C, D]
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            if ("2".equals(next)) {
                //Replaces the last element returned by #next or #previous
                log("before set", listIterator); //before set: pi=2, ni=3
                listIterator.set("22");
                log("after set", listIterator); //after set: pi=2, ni=3
                break;
            }
        }
        System.out.println(list); //[A, B, 22, C, D]
    }

    private static void remove(List<String> list) {
        System.out.println("remove");
        ListIterator<String> listIterator = list.listIterator();
        System.out.println(list); //[A, B, 22, C, D]
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            if ("22".equals(next)) {
                log("before remove", listIterator); //before remove: pi=2, ni=3
                listIterator.remove();
                log("after remove", listIterator); //after remove: pi=1, ni=2
                break;
            }
        }
        System.out.println(list); //[A, B, C, D]
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(
                Arrays.asList("A", "B", "C", "D")
        );
        System.out.println(list);
        forward(list.listIterator());
        backward(list.listIterator(list.size()));
        add(list);
        set(list);
        remove(list);
    }
}
