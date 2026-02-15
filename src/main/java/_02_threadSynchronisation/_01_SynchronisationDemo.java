package main.java._02_threadSynchronisation;

/*
===============================================
UNDERSTANDING THE synchronized KEYWORD
===============================================

WHAT HAPPENS WITHOUT synchronized?
- Both threads access the `increment()` method at the same time.
- `count++` is NOT atomic (it's a 3-step operation):
  1. Read the value of `count`
  2. Increment the value
  3. Write the new value back to `count`
- If two threads execute these steps simultaneously, they can overwrite each other's changes, leading to incorrect results (called a "race condition").

EXAMPLE OF RACE CONDITION:
1. Thread 1 reads `count = 5`
2. Thread 2 reads `count = 5`
3. Thread 1 increments to 6
4. Thread 2 increments to 6 (overwriting Thread 1's result)
Final value = 6 (should have been 7)

HOW DOES synchronized FIX THIS?
- The `synchronized` keyword creates a "lock" on the method or block.
- Only ONE thread can hold the lock at a time.
- While one thread is executing the synchronized method, all other threads trying to access it are forced to wait.
- This ensures that the `increment()` method is executed by one thread at a time, preventing race conditions.

REAL-WORLD ANALOGY:
- Imagine two people trying to use the same ATM.
- Without synchronized: Both can use the ATM at the same time, leading to errors (e.g., withdrawing more money than available).
- With synchronized: Only one person can use the ATM at a time, while the other waits for their turn.

IMPORTANT NOTES:
1. synchronized can be applied to:
   - Methods (e.g., `synchronized void increment()`)
   - Code blocks (e.g., `synchronized (this) { ... }`)
2. Synchronization adds overhead and can reduce performance, so use it only when necessary.
3. Synchronization ensures thread safety but does NOT guarantee execution order.
4. For static methods, the lock is on the class object (`SynchronisationDemo.class`).
5. For instance methods, the lock is on the instance (`this`).

KEY TAKEAWAY:
- Use `synchronized` to prevent race conditions when multiple threads access shared data.
- It ensures that only one thread can execute the synchronized code at a time.
*/

public class _01_SynchronisationDemo {
    public static int count = 0;

    public static synchronized  void increment() {
        count++;
    }

    // this is equivalent to :
    /*
    public static void increment() {
        synchronized (SynchronisationDemo.class) {  // Lock on the class object for static method
            count++;
        }
    }

    if this was not a static method, we would use:
    public void increment() {
        synchronized (this) {  // Lock on the instance for non-static method
            count++;
        }
    }
    */

    public static void main(String[] args) throws InterruptedException {
        
        Runnable task = () -> {
            {
                for (int i = 0; i < 10000 ; i++) {
                    increment();
                }
            }
        };

        Thread thread1 = new Thread(task);

        Thread thread2 = new Thread(task);

        thread1.start();
        long startTime = System.currentTimeMillis();
        thread2.start();
        thread1.join();
        thread2.join();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        
        thread1.join();
        thread2.join();
        
        System.out.println("Final count: " + count);  // Expected 2000, but may be less
    }
    
}
