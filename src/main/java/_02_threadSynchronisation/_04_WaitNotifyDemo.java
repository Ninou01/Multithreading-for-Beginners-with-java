package main.java._02_threadSynchronisation;

/*
===============================================
UNDERSTANDING wait() AND notify()
===============================================

WHAT IS wait()?
- `wait()` is used to make the current thread release the lock and enter the waiting state.
- The thread will remain in the waiting state until another thread calls `notify()` or `notifyAll()` on the same object.
- Must be called inside a `synchronized` block or method, and the thread must hold the lock on the object.

WHAT IS notify()?
- `notify()` wakes up **one** thread that is waiting on the same object's monitor (lock).
- The awakened thread will not immediately execute; it must first reacquire the lock.
- If multiple threads are waiting, the JVM decides which thread to wake up (not predictable).

WHAT IS notifyAll()?
- `notifyAll()` wakes up **all** threads that are waiting on the same object's monitor.
- All awakened threads will compete to reacquire the lock, and only one will proceed at a time.

WHY IS notifyAll() BETTER THAN notify()?
- With `notify()`, only one thread is awakened, which can lead to issues if the wrong thread is chosen (e.g., a thread that cannot proceed yet).
- `notifyAll()` ensures that all waiting threads are awakened, reducing the risk of deadlocks or missed signals.
- Use `notifyAll()` when multiple threads are waiting, and you want to ensure that all threads have a chance to proceed.

IMPORTANT NOTES:
1. Always call `wait()` and `notify()` inside a `synchronized` block or method.
2. The thread calling `wait()` releases the lock and enters the waiting state.
3. The thread calling `notify()` or `notifyAll()` does NOT release the lock immediately; it continues execution until the synchronized block/method ends.
4. `notify()` is more efficient when you know exactly which thread should be awakened.
5. `notifyAll()` is safer in scenarios where multiple threads are waiting, as it avoids potential deadlocks.

REAL-WORLD ANALOGY:
- `wait()`: A person waiting in line for their turn.
- `notify()`: The cashier calls the next person in line (but doesn't specify who).
- `notifyAll()`: The cashier announces that all counters are open, and everyone in line can compete for service.

KEY TAKEAWAY:
- Use `notify()` when you are certain only one thread needs to proceed.
- Use `notifyAll()` when multiple threads are waiting, and you want to avoid potential deadlocks or missed signals.
*/

public class _04_WaitNotifyDemo {
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two = new Thread(() -> {
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();
    }

    private static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method one...");
            LOCK.wait();
            System.out.println("Back Again in the method one");
        }
    }

    private static void two() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method two...");
            LOCK.notify(); // Remaining code lines in the block are executed
            System.out.println("Hello from method two even after notify...");
        }
    }
}