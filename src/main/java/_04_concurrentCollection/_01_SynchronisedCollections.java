package main.java._04_concurrentCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * ===============================================================
 *        Synchronization with Java Collections - Summary
 * ===============================================================
 *
 * 1) Problem
 * ---------------------------------------------------------------
 * Standard collections like ArrayList are NOT thread-safe.
 *
 * If multiple threads modify the same ArrayList at the same time,
 * race conditions may occur, leading to:
 *   - Incorrect size
 *   - Lost updates
 *   - Data corruption
 *
 *
 * 2) Manual Synchronization
 * ---------------------------------------------------------------
 * We can protect a collection using synchronized blocks:
 *
 *   synchronized(lock) {
 *       list.add(value);
 *   }
 *
 * This works if:
 *   - ALL accesses use the SAME lock
 *   - No one forgets to synchronize
 *
 * Drawback:
 *   - Error-prone in large systems
 *   - Easy to misuse
 *   - May reduce concurrency (coarse-grained locking)
 *
 *
 * 3) Collections.synchronizedList()
 * ---------------------------------------------------------------
 * Example:
 *
 *   List<Integer> list =
 *       Collections.synchronizedList(new ArrayList<>());
 *
 * This wraps the list so that all methods are synchronized internally.
 *
 * Benefit:
 *   - Thread safety is handled by the collection itself
 *   - No need to manually synchronize every access
 *   - Safer and cleaner in larger systems
 *
 *
 * 4) Important Note About Iteration
 * ---------------------------------------------------------------
 * Iteration is NOT automatically thread-safe because it involves
 * multiple method calls (iterator creation, hasNext(), next()).
 *
 * If another thread modifies the list during iteration,
 * it may cause inconsistent behavior or throw
 * ConcurrentModificationException.
 *
 * Therefore, we must manually synchronize the entire iteration:
 *
 *   synchronized(list) {
 *       for (Integer i : list) {
 *           System.out.println(i);
 *       }
 *   }
 *
 * 5) Modern Alternative
 * ---------------------------------------------------------------
 * Instead of synchronized collections, modern Java provides
 * concurrent collections such as:
 *
 *   - CopyOnWriteArrayList
 *   - ConcurrentHashMap
 *
 * These are designed for better scalability and performance
 * in multi-threaded environments.
 *
 * ===============================================================
 */


public class _01_SynchronisedCollections {
    public static void main(String[] args) {
        //List<Integer> list = new ArrayList<>();
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Thread one = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        one.start();
        two.start();

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Size of array : " + list.size());
    }
}
