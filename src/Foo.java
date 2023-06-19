import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;


public class Foo {
    public static void main(String[] args) {
        Foo foo = new Foo();

        CompletableFuture.runAsync(() -> {
           foo.first(new Thread(Thread.currentThread()));
        });

        CompletableFuture.runAsync(() -> {
            try {
                foo.second(new Thread(Thread.currentThread()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                foo.third(new Thread(Thread.currentThread()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private final Semaphore s1 = new Semaphore(0);
    private final Semaphore s2 = new Semaphore(0);
    public void first (Runnable r) {
        System.out.println("first");
        s1.release();
    }
    public void second (Runnable r) throws InterruptedException {
        s1.acquire();
        System.out.println("second");
        s2.release();
    }
    public void third(Runnable r) throws InterruptedException {
        s2.acquire();
        System.out.println("third");
    }
}