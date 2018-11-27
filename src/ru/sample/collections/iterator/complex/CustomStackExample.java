package ru.sample.collections.iterator.complex;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Created by Nechaev Mikhail
 * Since 27/11/2018.
 */
public class CustomStackExample {

    public static void main(String[] args) {
        CustomStack stack = new CustomStack(4);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        iterator = stack.iterator();
        stack.pop();
        try {
            iterator.hasNext();
        } catch (ConcurrentModificationException expected) {
            System.out.println(expected.getMessage());
        }
    }
}

class CustomStack {

    private final int[] data;
    private int size = 0;
    private int modCount = 0;

    public CustomStack(int capacity) {
        if (capacity < 1 || capacity > 10) {
            throw new IllegalArgumentException("capacity must be in range [1..10]");
        }
        data = new int[capacity];
    }

    void push(int value) {
        if (size == data.length) {
            throw new IllegalStateException("stack is full");
        }
        modCount++;
        data[size++] = value;
    }

    int pop() {
        if (size == 0) {
            throw new IllegalStateException("stack is empty");
        }
        modCount++;
        return data[--size];
    }

    int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    Iterator<Integer> iterator() {

        return new Iterator<>() {
            int position = 0;
            int currentSize = size; //or CustomStack.this.size
            int currentModCount = CustomStack.this.modCount; //or modCount

            @Override
            public boolean hasNext() {
                checkForComodification();
                return position < currentSize;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return CustomStack.this.data[position++];
            }

            private void checkForComodification() {
                if (currentModCount != modCount) { //or CustomStack.this.modCount
                    throw new ConcurrentModificationException("stack was changed");
                }
            }
        };
    }
}
