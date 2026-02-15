package main.java._02_threadSynchronisation;

/*
===============================================
synchronized: METHOD-LEVEL vs BLOCK-LEVEL
===============================================

WHAT'S THE DIFFERENCE?
- **Method-level synchronized**: Locks the entire method, meaning the lock is on the object (or class for static methods).
- **Block-level synchronized**: Locks only the specific block of code, allowing finer-grained control.

DOWNSIDE OF METHOD-LEVEL synchronized:
1. **Single Lock for Entire Object/Class**:
   - When a thread acquires the lock, it blocks ALL synchronized methods on the same object (or class for static methods).
   - This can lead to unnecessary contention, even if the methods operate on different data.

2. **Reduced Concurrency**:
   - Threads that could safely execute different synchronized methods in parallel are forced to wait.
   - This reduces performance and scalability.

3. **Over-Synchronization**:
   - Locks more code than necessary, increasing the risk of deadlocks and reducing efficiency.

EXAMPLE OF METHOD-LEVEL synchronized:
```java
public synchronized static void increment1() {
    count1++;
}

public synchronized static void increment2() {
    count2++;
}
```
- Here, both `increment1` and `increment2` are synchronized on the same class-level lock (`LockWithCustomObject.class`).
- If Thread 1 is executing `increment1`, Thread 2 must wait to execute `increment2`, even though the two methods operate on different data (`count1` and `count2`).

HOW BLOCK-LEVEL synchronized FIXES THIS:
- By using separate locks (`lock1` and `lock2`), we ensure that threads can execute `increment1` and `increment2` simultaneously, as they operate on independent data.

REAL-WORLD ANALOGY:
- Imagine two bank tellers (threads) helping customers (methods).
- **Method-level synchronized**: Only one teller can work at a time, even if they are helping different customers.
- **Block-level synchronized**: Each teller works independently, as long as they are helping different customers.

WHEN TO USE BLOCK-LEVEL synchronized:
- When different threads access independent data or resources.
- When you want to minimize contention and maximize concurrency.

KEY TAKEAWAY:
- Use **block-level synchronized** with custom lock objects for better performance and fine-grained control.
- Reserve **method-level synchronized** for simple cases where all synchronized methods operate on the same shared resource.
*/

public class _02_LockWithCustomObject {
    private static int count1 = 0;
    private static int count2 = 0;

    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    public static void increment1() {
        synchronized (lock1) {
            count1++;
        }
    }

    public static void increment2() {
        synchronized (lock2) {
            count2++;
        } 
    }

    public static void main(String[] args) throws InterruptedException {
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                increment1();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                increment2();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Count1: " + count1);  // Expected 100000
        System.out.println("Count2: " + count2);  // Expected 100000 
    }
}
