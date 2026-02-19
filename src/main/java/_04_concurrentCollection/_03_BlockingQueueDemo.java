package main.java._04_concurrentCollection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
===============================================
UNDERSTANDING BlockingQueue
===============================================

WHAT IS BlockingQueue?
- A thread-safe queue that supports operations like `put()` and `take()` which block the thread if the queue is full or empty.
- It is part of the `java.util.concurrent` package and is designed for scenarios where threads need to communicate via a shared queue.

HOW IT WORKS:
1. **Producer**:
   - Calls `put()` to add an item to the queue.
   - If the queue is full, the producer thread is **blocked** until space becomes available.

2. **Consumer**:
   - Calls `take()` or `poll()` to retrieve an item from the queue.
   - If the queue is empty, the consumer thread is **blocked** until an item is added.

WHY USE BlockingQueue?
- Simplifies the implementation of the **Producer-Consumer Pattern**.
- Automatically handles thread synchronization, so you don't need to use `wait()` and `notify()` manually.
- Prevents race conditions and ensures thread safety.

TYPES OF BLOCKING QUEUES:
1. **ArrayBlockingQueue**:
   - A fixed-size queue backed by an array.
   - Blocks producers when full and consumers when empty.
   - Example: `new ArrayBlockingQueue<>(capacity)`

2. **LinkedBlockingQueue**:
   - A queue backed by a linked list.
   - Can be bounded (fixed size) or unbounded (default).
   - Useful for scenarios where the queue size may grow dynamically.

3. **PriorityBlockingQueue**:
   - A priority queue where elements are ordered based on their natural ordering or a custom comparator.
   - Does not block producers (unbounded by default).

4. **DelayQueue**:
   - A queue where elements are only available for consumption after a specific delay.

5. **SynchronousQueue**:
   - A queue with no capacity; each `put()` must wait for a `take()` and vice versa.

WHEN TO USE BlockingQueue:
- When you need to implement the producer-consumer pattern.
- When you want to avoid manually handling thread synchronization.
- When you need a thread-safe queue for concurrent tasks.

WHY ArrayBlockingQueue FITS THE PRODUCER-CONSUMER PATTERN:
- Fixed capacity ensures that producers are blocked when the queue is full.
- Consumers are blocked when the queue is empty, ensuring proper coordination between threads.
- Simplifies the implementation by handling synchronization internally.

IMPORTANT NOTES:
1. **Thread Safety**:
   - BlockingQueue handles all synchronization internally, so you don't need to use `synchronized`, `wait()`, or `notify()`.

2. **Blocking Methods**:
   - `put()` → Blocks if the queue is full.
   - `take()` → Blocks if the queue is empty.

3. **Non-Blocking Methods**:
   - `offer()` → Adds an item to the queue if space is available, otherwise returns `false`.
   - `poll()` → Retrieves and removes an item if available, otherwise returns `null`.

4. **Timeout Variants**:
   - `offer(E e, long timeout, TimeUnit unit)` → Waits for space to become available.
   - `poll(long timeout, TimeUnit unit)` → Waits for an item to become available.

REAL-WORLD ANALOGY:
- Imagine a restaurant kitchen:
  - The chef (producer) prepares dishes and places them on a serving counter (queue).
  - The waiter (consumer) picks up dishes from the counter to serve customers.
  - If the counter is full, the chef must wait until the waiter picks up a dish.
  - If the counter is empty, the waiter must wait until the chef prepares a dish.

KEY TAKEAWAY:
- Use `BlockingQueue` for thread-safe communication between producers and consumers.
- `ArrayBlockingQueue` is ideal for fixed-capacity scenarios where you want to limit the queue size.
*/

public class _03_BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    System.out.println("Producing " + i);
                    queue.put(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Integer value = queue.poll(0, TimeUnit.SECONDS);
                    if (value != null) {
                        System.out.println("Consuming " + value);

                    } else {
                        System.out.println("Queue is empty, waiting for items...");
                    }
                    // Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();


    }
}
