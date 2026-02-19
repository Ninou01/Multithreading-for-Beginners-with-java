package main.java._04_concurrentCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
===============================================
UNDERSTANDING ConcurrentHashMap
===============================================

WHAT IS ConcurrentHashMap?
- A high-performance, thread-safe implementation of a hash-based map.
- Designed for concurrent access by multiple threads without requiring external synchronization.
- Part of the `java.util.concurrent` package.

HOW IT WORKS:
1. **Fine-Grained Locking**:
   - Instead of locking the entire map, it uses **bucket-level locks** (segments) for write operations.
   - This allows multiple threads to read and write to different buckets concurrently.

2. **Non-Blocking Reads**:
   - Read operations (`get()`) are non-blocking and do not require locks.
   - This ensures high performance for read-heavy workloads.

3. **Thread Safety**:
   - Ensures thread safety for all operations, including `put()`, `get()`, and `remove()`.
   - No need to use `synchronized` blocks or external locks.

WHY USE ConcurrentHashMap?
- To achieve thread-safe access to a map without the performance overhead of synchronizing the entire map.
- Ideal for scenarios with high concurrency where multiple threads frequently read and write to the map.

KEY FEATURES:
1. **Thread-Safe**:
   - All operations are thread-safe, including compound actions like `putIfAbsent()` and `computeIfAbsent()`.

2. **High Performance**:
   - Fine-grained locking ensures better performance compared to `Collections.synchronizedMap()`.

3. **No Null Keys/Values**:
   - Unlike `HashMap`, `ConcurrentHashMap` does not allow `null` keys or values.

4. **Atomic Operations**:
   - Provides atomic methods like `putIfAbsent()`, `compute()`, and `merge()` for thread-safe updates.

WHEN TO USE ConcurrentHashMap:
- When multiple threads need to access and modify a shared map concurrently.
- For read-heavy workloads where high performance is critical.
- When you need atomic operations on map entries (e.g., `computeIfAbsent()`).

WHEN NOT TO USE:
- For single-threaded applications (use `HashMap` instead).
- When you need to allow `null` keys or values (use `HashMap` or `Hashtable`).

REAL-WORLD ANALOGY:
- Think of a library with multiple sections (buckets):
  - Multiple readers can browse books in different sections simultaneously.
  - If a librarian is updating a section, only that section is locked, allowing other sections to remain accessible.

IMPORTANT NOTES:
1. **Read Operations**:
   - Non-blocking and highly efficient.
   - Multiple threads can read concurrently without contention.

2. **Write Operations**:
   - Use fine-grained locking, so only the bucket being modified is locked.

3. **Atomic Methods**:
   - `putIfAbsent(key, value)` → Adds the key-value pair only if the key is not already present.
   - `compute(key, remappingFunction)` → Atomically updates the value for the given key.
   - `merge(key, value, remappingFunction)` → Combines the existing value with a new value.

4. **No Null Keys/Values**:
   - `ConcurrentHashMap` does not allow `null` keys or values to avoid ambiguity in concurrent scenarios.

EXAMPLE USE CASES:
- Caching frequently accessed data in a multi-threaded application.
- Storing shared configuration or state in a concurrent environment.
- Implementing thread-safe counters or aggregators.

KEY TAKEAWAY:
- Use `ConcurrentHashMap` for high-performance, thread-safe access to a shared map.
- It is optimized for concurrent reads and writes, making it ideal for multi-threaded applications.
*/

public class _04_ConcurrentHashMapDemo {
    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for(int i=0; i<10; i++) {
            int threadNum = i;
            new Thread(() -> {
                String key = "key @ " + threadNum;
                for (int j = 0; j < 5; j++) {
                    String value = getCachedValue(key);
                    System.out.println(Thread.currentThread().getName() + " - " + key + ": " + value);
                }
            }).start();
        }
    }

    public static String compute(String key) {
        System.out.println(key + " not present in the cache, so going to compute!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Value for " + key;
    }

    public static String getCachedValue(String key) {
        String value = cache.get(key);
        if (value == null) {
            value = compute(key);
            cache.put(key, value);
        }
        return value;
    }

}
