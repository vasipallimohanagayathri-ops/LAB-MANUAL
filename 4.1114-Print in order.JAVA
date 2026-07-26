import java.util.concurrent.CountDownLatch;

public class Foo {
    private final CountDownLatch latch1 = new CountDownLatch(1);
    private final CountDownLatch latch2 = new CountDownLatch(1);

    public Foo() {}

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        latch1.countDown(); // Let second() proceed
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latch1.await();     // Wait until first() finishes
        printSecond.run();
        latch2.countDown(); // Let third() proceed
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch2.await();     // Wait until second() finishes
        printThird.run();
    }

    // Testing the Foo class
    public static void main(String[] args) {
        Foo foo = new Foo();

        Thread t1 = new Thread(() -> {
            try {
                foo.first(() -> System.out.print("first"));
            } catch (InterruptedException e) {}
        });

        Thread t2 = new Thread(() -> {
            try {
                foo.second(() -> System.out.print("second"));
            } catch (InterruptedException e) {}
        });

        Thread t3 = new Thread(() -> {
            try {
                foo.third(() -> System.out.print("third"));
            } catch (InterruptedException e) {}
        });

        // Start the threads in random order
        t3.start();
        t2.start();
        t1.start();
    }
}
