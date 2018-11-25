package ru.sample.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
@SuppressWarnings("unused")
class AnonymousClass2 {

    interface Interface {
        void method();
    }

    abstract class Class {
        Class(int x) {
            System.out.println(x);
        }

        abstract void method();
    }

    @SuppressWarnings("Convert2Lambda")
    private void run() {
        Interface i = new Interface() {
            @Override public void method() {
                System.out.println("Interface method");
            }
        };
        //Интерфейс с одним методом может быть заменён на лямбду
        Interface i2 = () -> {};

        Class c = new Class(5) {
            @Override void method() {}
        };
        class C2 extends Class {

            C2() {
                super(2);
            }

            @Override
            void method() {
                System.out.println("C2 method");
            }
        }
    }
    public static void main(String[] args) {
        new AnonymousClass2().run();
    }
}
