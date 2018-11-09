package ru.mail.polis.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class AnonymousClass3 {
    interface I { void m(); }
    static int a = 1; //member enclosing class
    int b = 2; //member enclosing class
    private void run(
            int c /*local scope: effective final. Java 8 */, int d) {
        d--; //local scope: NON effective final
        int e = 5; e++; //local scope: NON effective final
        final int f = 6; //local scope: final
        int g = 7; //local scope: effective final
        new I() {
            @Override public void m() {
                System.out.println(
                    a + b + c + f + g //+ d + e
                );
                int g = 10; //shadowing
                System.out.println(g);
            }
        }.m();
    }
    public static void main(String[] args) {
        new AnonymousClass3().run(3, 4);
    }
}
