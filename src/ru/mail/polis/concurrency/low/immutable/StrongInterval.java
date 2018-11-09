package ru.mail.polis.concurrency.low.immutable;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
final public class StrongInterval {

    private final String name;
    private final int left;
    private final int right;

    public StrongInterval(String name, int left, int right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    public String getName() {
        return name; //String is immutable
    }

    public boolean inside(int coordinate) {
        return left <= coordinate && coordinate <= right;
    }

    public static void main(String[] args) {
        System.out.println(new StrongInterval("b", 1, 2).inside(1));
    }
}
