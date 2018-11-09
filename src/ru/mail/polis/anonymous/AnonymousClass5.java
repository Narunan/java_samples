package ru.mail.polis.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class AnonymousClass5 {
    interface I {}
    private void run() {
        new I() {
            { //instance initializer
                System.out.println("init" + this.x); //0
            }
            int x = 5;
            void method() {
                System.out.println("method");
                new C().run();
            }
            {
                System.out.println("init" + this.x); //5
            }
            class C {
                void run() {
                    System.out.println("C");
                }
            }
        }.method();
    }
    public static void main(String[] args) {
        new AnonymousClass5().run();
    }
}
