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
        CustomStack<String>stack = new CustomStack<>(4);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");
        Iterator<String> iterator = stack.iterator();
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

class CustomStack<E> {

    private final E[] data;
    private int size = 0;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public CustomStack(int capacity) {
        if (capacity < 1 || capacity > 10) {
            throw new IllegalArgumentException("capacity must be in range [1..10]");
        }
        data = (E[]) new Object[capacity];
    }

    void push(E value) {
        if (size == data.length) {
            throw new IllegalStateException("stack is full");
        }
        modCount++;
        data[size++] = value;
    }

    E pop() {
        if (size == 0) {
            throw new IllegalStateException("stack is empty");
        }
        modCount++;
        E value = data[--size];
        data[size] = null; //for GC
        return value;
    }

    int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    Iterator<E> iterator() {

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
            public E next() {
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
