package ru.mail.polis.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class AnonymousClass2 {
    interface Interface {
        void method();
    }
    abstract class Class {
        Class(int x) {
            System.out.println(x);
        }
        abstract void method();
    }
    private void run() {
        Interface i = new Interface() {
            @Override public void method() {}
        };
        //Интерфейс с одним методом может быть заменён на лямбду
        Interface i2 = () -> {};

        Class c = new Class(5) {
            @Override void method() {}
        };
    }
    public static void main(String[] args) {
        new AnonymousClass2().run();
    }
}
