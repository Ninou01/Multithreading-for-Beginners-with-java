# Java Multithreading: Concurrent Collections

This folder contains examples and lessons on Java's **concurrent collections** and synchronization utilities, which are part of the `java.util.concurrent` package. These tools are designed to simplify multithreaded programming by providing thread-safe data structures and utilities for coordinating threads.

## Topics Covered

1. **Synchronized Collections** (`_01_SynchronisedCollections.java`)
   - Demonstrates how to use `Collections.synchronizedList()`, `synchronizedMap()`, etc., to make standard collections thread-safe.
   - **Key Point**: These are simple wrappers around existing collections but require external synchronization for compound actions (e.g., iteration).

2. **CountDownLatch** (`_02_CountDownLatchDemo.java`)
   - A synchronization utility that allows one or more threads to wait until a set of operations in other threads completes.
   - **Use Case**: Waiting for multiple tasks to finish before proceeding.
   - **Key Point**: Useful for event-based waiting and when thread references are not directly accessible.

3. **BlockingQueue** (`_03_BlockingQueueDemo.java`)
   - A thread-safe queue that blocks producers when the queue is full and consumers when the queue is empty.
   - **Use Case**: Perfect for implementing the **Producer-Consumer Pattern**.
   - **Key Point**: `ArrayBlockingQueue` is a fixed-capacity queue that handles synchronization internally.

4. **ConcurrentHashMap** (`_04_ConcurrentHashMapDemo.java`)
   - A high-performance, thread-safe hash-based map that uses fine-grained locking for write operations and non-blocking reads.
   - **Use Case**: Shared maps in multi-threaded environments with frequent reads and writes.
   - **Key Point**: Provides atomic operations like `putIfAbsent()` and `compute()`.

5. **CyclicBarrier** (`_05_CyclicBarrierDemo.java`)
   - A synchronization utility that allows a group of threads to wait for each other at a common barrier point.
   - **Use Case**: Useful for tasks that require all threads to reach a certain point before proceeding (e.g., parallel computations).
   - **Key Point**: Unlike `CountDownLatch`, it can be reused.

6. **Exchanger** (`_06_ExchangerDemo.java`)
   - A synchronization utility that allows two threads to exchange data at a synchronization point.
   - **Use Case**: Useful for scenarios where two threads need to swap data (e.g., producer-consumer with intermediate processing).
   - **Key Point**: Both threads must call `exchange()` to complete the data exchange.

7. **CopyOnWriteArrayList** (`_07_COWADemo.java`)
   - A thread-safe variant of `ArrayList` where all mutative operations (e.g., `add`, `remove`) create a new copy of the underlying array.
   - **Use Case**: Ideal for read-heavy workloads with infrequent writes.
   - **Key Point**: Provides thread safety without requiring external synchronization but is inefficient for frequent writes.

---

## Key Concepts

### Synchronized Collections vs Concurrent Collections
| Feature                  | Synchronized Collections       | Concurrent Collections       |
|--------------------------|--------------------------------|------------------------------|
| Thread Safety            | Yes (via external locks)      | Yes (built-in synchronization) |
| Performance              | Slower (global lock)          | Faster (fine-grained locking) |
| Compound Actions         | Not thread-safe               | Thread-safe                  |
| Examples                 | `Collections.synchronizedList` | `ConcurrentHashMap`, `BlockingQueue` |

### Comparison of Utilities
| Utility          | Purpose                                   | Key Feature                              |
|-------------------|-------------------------------------------|------------------------------------------|
| CountDownLatch    | Wait for multiple threads to complete     | One-time use                             |
| CyclicBarrier     | Synchronize threads at a common point     | Reusable                                 |
| BlockingQueue     | Thread-safe queue for producer-consumer   | Blocks on full/empty queue               |
| ConcurrentHashMap | Thread-safe map for concurrent access     | Fine-grained locking, atomic operations  |
| Exchanger         | Exchange data between two threads         | Both threads must call `exchange()`      |
| CopyOnWriteArrayList | Thread-safe list for read-heavy workloads | Creates a new copy on write operations   |

---

## Best Practices

1. **Choose the Right Tool**:
   - Use `ConcurrentHashMap` for maps with frequent reads and writes.
   - Use `BlockingQueue` for producer-consumer scenarios.
   - Use `CyclicBarrier` for reusable synchronization points.
   - Use `CopyOnWriteArrayList` for read-heavy workloads with minimal writes.

2. **Avoid Over-Synchronization**:
   - Prefer concurrent collections over manually synchronizing standard collections.

3. **Understand the Trade-offs**:
   - `CopyOnWriteArrayList` is efficient for reads but expensive for writes.
   - `BlockingQueue` simplifies producer-consumer logic but may block threads.

4. **Graceful Shutdown**:
   - Always shut down executors or threads gracefully when using these utilities.

---

## How to Use
- Each file contains detailed comments explaining the concepts and code.
- Run the examples to see how each utility behaves in a multi-threaded environment.
- Use this folder as a reference for understanding and implementing concurrent collections and synchronization utilities in Java.
