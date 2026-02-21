package main.java._04_concurrentCollection;

import java.util.concurrent.CopyOnWriteArrayList;

/*
 * ===============================================
 *         UNDERSTANDING CopyOnWriteArrayList
 * ===============================================
 *
 * 1) What is CopyOnWriteArrayList?
 * ------------------------------------------------
 * CopyOnWriteArrayList is a thread-safe List implementation
 * where every write operation (add, remove, set) creates
 * a new copy of the internal array.
 *
 * Reads do NOT require locking.
 *
 *
 * 2) Core Idea (Copy-On-Write)
 * ------------------------------------------------
 * When a modification happens:
 *
 *     - A new copy of the internal array is created
 *     - The modification is applied to the copy
 *     - The reference is updated to the new array
 *
 * Readers continue using the old array safely.
 *
 *
 * 3) Why Is It Thread-Safe?
 * ------------------------------------------------
 * - Readers access a stable snapshot of the array.
 * - Writers create a new array instead of modifying
 *   the existing one.
 * - No race conditions during iteration.
 *
 *
 * 4) Iteration Behavior
 * ------------------------------------------------
 * Iterators work on a snapshot of the array.
 *
 * This means:
 *     - No ConcurrentModificationException
 *     - Safe iteration even if the list is modified
 *       by another thread.
 *
 *
 * 5) Performance Characteristics
 * ------------------------------------------------
 * Reads:
 *     - Very fast
 *     - No locking required
 *
 * Writes:
 *     - Expensive (O(n))
 *     - Entire array is copied on each modification
 *
 *
 * 6) When To Use It
 * ------------------------------------------------
 * Best for:
 *     - Many reads
 *     - Few writes
 *     - Listener lists
 *     - Event subscription lists
 *     - Configuration data
 *
 *
 * 7) When NOT To Use It
 * ------------------------------------------------
 * Avoid when:
 *     - Frequent modifications
 *     - Very large lists with many writes
 *
 *
 * 8) Comparison With Other Thread-Safe Lists
 * ------------------------------------------------
 * synchronizedList:
 *     - Uses one big lock
 *     - Slower under high contention
 *
 * CopyOnWriteArrayList:
 *     - No locking for reads
 *     - Better scalability for read-heavy workloads
 *
 * ===============================================
 */

public class _07_COWADemo {
    public static void main(String[] args) {

        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        Thread threadOne = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        });

        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("Size of list: " + list.size());
        }
        
    }
}
