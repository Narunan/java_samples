package ru.mail.polis.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class AnonymousClass4 {
    interface I {}
    private void run() {
        I i = new I() {
            int a;
            //inner class cannot have static declaration
            //static {
            //    a = 3;
            //}
            //inner class cannot have static declaration
            //static int b = 4;
            final static int c = 5;
            //inrerface are not allowed here
            //interface I2 {}
            //ok
            class C {}
        };
    }
    public static void main(String[] args) {
        new AnonymousClass4().run();
    }
}
