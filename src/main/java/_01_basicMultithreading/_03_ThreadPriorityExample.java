package main.java._01_basicMultithreading;

/*
===============================================
THREAD PRIORITY IN JAVA
===============================================

WHAT IS THREAD PRIORITY?
- A hint to the JVM about which threads should get more CPU time
- Range: 1 (MIN_PRIORITY) to 10 (MAX_PRIORITY)
- Default: 5 (NORM_PRIORITY)

CONSTANTS YOU CAN USE:
Thread.MIN_PRIORITY  = 1
Thread.NORM_PRIORITY = 5  (default)
Thread.MAX_PRIORITY  = 10

HOW IT WORKS:
- Higher priority threads are MORE LIKELY to execute first
- BUT it's NOT guaranteed! The OS scheduler makes final decisions
- Threads inherit priority from their parent thread

IMPORTANT GOTCHA:
Thread priority is a SUGGESTION, not a guarantee!
- Different operating systems handle priorities differently
- JVM passes priority hints to OS, but OS decides final scheduling
- Don't rely on priority for critical synchronization
- Two threads with same priority? Execution order is unpredictable

WHEN PRIORITY MATTERS:
✓ CPU-bound tasks competing for resources
✓ Background vs foreground tasks
✗ Don't use for correctness (use locks/synchronization instead)
✗ Not reliable for precise execution order

REAL-WORLD ANALOGY:
Think of it like express lanes at airport security:
- Priority 10 = First class (usually goes first, but not always)
- Priority 5 = Regular (standard line)
- Priority 1 = Standby (goes when there's room)
*/

public class _03_ThreadPriorityExample {
    public static void main(String[] args) {
        // Main thread starts with default priority 5
        System.out.println(Thread.currentThread().getName());      // "main"
        System.out.println(Thread.currentThread().getPriority());  // 5 (default)
        
        // Change main thread to highest priority
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);   // Set to 10
        System.out.println(Thread.currentThread().getPriority());  // 10
        
        /*
        COMMON PATTERN:
        Thread highPriority = new Thread(() -> {
            // Important task
        });
        highPriority.setPriority(Thread.MAX_PRIORITY);  // 10
        highPriority.start();
        
        Thread lowPriority = new Thread(() -> {
            // Background task
        });
        lowPriority.setPriority(Thread.MIN_PRIORITY);  // 1
        lowPriority.start();
        
        NOTE: highPriority will PROBABLY run more often, but NOT guaranteed!
        */
    }
}

/*
QUICK TIPS:
- Child threads inherit parent's priority by default
- setPriority() can be called before or after start()
- Priority outside 1-10 range throws IllegalArgumentException
- Most modern apps don't need to mess with priorities
*/