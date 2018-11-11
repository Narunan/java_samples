package ru.mail.polis.anonymous;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
@SuppressWarnings("unused")
class AnonymousClass3 {
    interface I { void m(); }
    private static final int A = 1; //member enclosing class
    private final int b = 2; //member enclosing class

    @SuppressWarnings({"SameParameterValue", "Convert2Lambda", "TrivialFunctionalExpressionUsage", "UnusedAssignment"})
    private void run(
            int c /*local scope: effective final. Java 8 */, int d) {
        d--; //local scope: NON effective final
        int e = 5; e++; //local scope: NON effective final
        final int f = 6; //local scope: final
        int g = 7; //local scope: effective final
        new I() {
            @Override public void m() {
                System.out.println(
                    A + b + c + f + g //+ d + e
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
