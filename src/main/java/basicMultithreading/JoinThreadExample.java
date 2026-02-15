package main.java.basicMultithreading;

/*
===============================================
WHAT IS .join() METHOD IN JAVA?
===============================================

DEFINITION:
- The join() method makes the current thread WAIT until the thread on which join() is called completes execution
- It's used to ensure thread execution order and synchronization

KEY POINTS FOR EXAM:
1. Thread.join() causes the calling thread to PAUSE/BLOCK until the target thread finishes
2. Throws InterruptedException (must be handled)
3. Variants:
   - join() → waits indefinitely until thread dies
   - join(long millis) → waits maximum millis milliseconds
   - join(long millis, int nanos) → waits maximum millis + nanos time

WHY USE join()?
- To ensure a thread completes before continuing execution
- To maintain execution order between threads
- To wait for background tasks to finish before proceeding


IMPORTANT: join() does NOT guarantee execution order of threads,
it only guarantees that the calling thread waits for the target thread to finish!
*/

public class JoinThreadExample {
    public static void main(String[] args) throws InterruptedException {
        
        // Thread 1: prints 5 numbers
        Thread one = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1 : " + i);
            }
        });

        // Thread 2: prints 25 numbers
        Thread two = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                System.out.println("Thread 2 : " + i);
            }
        });

        System.out.println("Before invoking threads...");
        
        // Start both threads - they run CONCURRENTLY (at the same time)
        one.start();  // Thread 1 starts execution
        two.start();  // Thread 2 starts execution
        
        // CRITICAL LINE: main thread waits here until Thread 1 completes
        // Without join(): "Done executing..." might print before threads finish
        // With join(): "Done executing..." prints ONLY after Thread 1 finishes
        one.join();
        
        // NOTE: Thread 2 might still be running when this prints!
        // We only waited for Thread 1 to finish, NOT Thread 2
        System.out.println("Done executing the threads!");
        
        /*
        EXECUTION FLOW:
        1. Main thread starts
        2. Thread 1 and Thread 2 start (run concurrently)
        3. Main thread hits one.join() → BLOCKS/WAITS
        4. Thread 1 finishes → main thread UNBLOCKS
        5. Main prints "Done executing..."
        6. Thread 2 might still be running!
        
        TO WAIT FOR BOTH THREADS:
        one.join();
        two.join();  // Add this line!
        
        ALTERNATIVE - WAIT WITH TIMEOUT:
        one.join(1000);  // Wait max 1 second
        */
    }
}
