package ru.sample.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
class AnonymousClass4 {
    private interface I {}

    @SuppressWarnings("unused")
    private void run() {
        I i = new I() {
            int a;
            //inner class cannot have static declaration
            //static {
            //    a = 3;
            //}
            //inner class cannot have static declaration
            //static int b = 4;
            static final int C = 5;
            //interface are not allowed here
            //interface I2 {}
            //ok
            class C {}
        };
    }
    public static void main(String[] args) {
        new AnonymousClass4().run();
    }
}
