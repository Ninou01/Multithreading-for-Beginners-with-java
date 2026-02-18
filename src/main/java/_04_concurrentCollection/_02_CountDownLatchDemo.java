package main.java._04_concurrentCollection;

import java.util.concurrent.CountDownLatch;

/*
===============================================
UNDERSTANDING CountDownLatch
===============================================

WHAT IS CountDownLatch?
- A synchronization helper that allows one or more threads to wait until a set of operations 
  being performed by other threads completes.
- Think of it as a countdown counter: threads wait until the counter reaches zero.

HOW IT WORKS:
1. Initialize with a count: `new CountDownLatch(n)`
2. Threads call `countDown()` to decrement the counter
3. Other threads call `await()` to wait until the counter reaches zero
4. Once counter hits zero, all waiting threads are released

KEY METHODS:
- `await()` → Blocks the calling thread until count reaches zero
- `await(timeout, unit)` → Waits with a timeout
- `countDown()` → Decrements the count by 1
- `getCount()` → Returns current count

WHY USE CountDownLatch INSTEAD OF join()?
1. **Event-Based Waiting** (not thread-based):
   - join() waits for entire thread to complete
   - CountDownLatch waits for specific events/tasks within threads
   - Example: Wait for 3 tasks to complete, even if threads continue running

2. **No Thread Reference Needed**:
   - join() requires direct access to Thread object
   - CountDownLatch works with Executor Service where you don't have thread references
   - Just pass the latch to tasks and call countDown() when done

3. **Complex Coordination**:
   - join() only works for simple "wait until thread finishes" scenarios
   - CountDownLatch handles complex scenarios like "wait until N events complete"

4. **Multiple Waiters**:
   - Multiple threads can wait on the same latch
   - All are released simultaneously when count reaches zero

WHEN TO USE CountDownLatch:
✓ Waiting for multiple tasks to complete before proceeding
✓ Using Executor Service (no direct thread access)
✓ Coordinating complex startup/shutdown sequences
✓ Waiting for specific events, not entire thread completion
✓ Business logic requires tracking task completion, not thread lifecycle

WHEN TO USE join():
✓ Simple scenarios with direct thread references
✓ Need to wait for entire thread to finish
✓ Small-scale applications with straightforward logic

REAL-WORLD ANALOGY:
- Imagine a restaurant preparing a multi-course meal:
  - CountDownLatch(3) → Wait for 3 dishes to be ready
  - Each chef calls countDown() when their dish is done
  - Main thread (waiter) calls await() and waits until all 3 dishes are ready
  - Once all dishes are ready (count = 0), service begins!

IMPORTANT NOTES:
1. CountDownLatch is a one-time use object (cannot be reset)
2. For reusable countdown, use CyclicBarrier instead
3. countDown() can be called from any thread
4. Multiple threads can await() on the same latch
5. Once count reaches zero, subsequent await() calls return immediately

KEY TAKEAWAY:
- Use CountDownLatch when you need to coordinate based on EVENTS/TASKS
- Use join() when you need to coordinate based on THREAD COMPLETION
*/

public class _02_CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int numberOfChefs = 3;
        
        // Create latch with count of 3 (waiting for 3 events)
        CountDownLatch latch = new CountDownLatch(numberOfChefs);

        // Chefs start preparing their dishes
        // Note: We don't need to keep Thread references!
        new Thread(new Chef("Chef A", "Pizza", latch)).start();
        new Thread(new Chef("Chef B", "Pasta", latch)).start();
        new Thread(new Chef("Chef C", "Salad", latch)).start();

        // Main thread waits here until all 3 chefs finish their dishes
        // This is where await() blocks until count reaches 0
        latch.await();

        // This line executes only after all 3 countDown() calls
        System.out.println("All dishes are ready! Let's start serving customers.");
    }
}

class Chef implements Runnable {
    private final String name;
    private final String dish;
    private final CountDownLatch latch;

    public Chef(String name,
                String dish,
                CountDownLatch latch) {
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is preparing " + dish);
            
            // Simulate cooking time (2 seconds)
            Thread.sleep(2000);
            
            System.out.println(name + " has finished preparing " + dish);
            
            // CRITICAL: Signal that this task is complete
            // Decrements the latch count by 1
            latch.countDown();
            
            // Note: Thread continues running after countDown()
            // This is different from join() which waits for thread to DIE
            
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
