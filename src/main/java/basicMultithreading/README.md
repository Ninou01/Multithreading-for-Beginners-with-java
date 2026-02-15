# Java Multithreading: Basic Concepts

This folder contains examples and lessons on the fundamental concepts of multithreading in Java. Each file demonstrates a specific topic, with clear examples and explanations.

## Topics Covered

1. **Thread Creation: Runnable vs Thread** (`RunnableThreadExample.java`)
   - Demonstrates the two ways to create threads in Java:
     - Implementing the `Runnable` interface (preferred approach).
     - Extending the `Thread` class.
   - Explains when to use each approach and their advantages/disadvantages.

2. **Thread Join** (`JoinThreadExample.java`)
   - Explains the `join()` method, which allows one thread to wait for another to complete.
   - Covers different variants of `join()`:
     - `join()`
     - `join(long millis)`
     - `join(long millis, int nanos)`
   - Use cases for ensuring thread execution order.

3. **Thread Priority** (`ThreadPriorityExample.java`)
   - Explains thread priorities in Java, ranging from `Thread.MIN_PRIORITY` (1) to `Thread.MAX_PRIORITY` (10).
   - Discusses how thread priorities are hints to the JVM and are not guaranteed.
   - Real-world analogy: Express lanes at airport security.

4. **Daemon vs User Threads** (`DaemonUserThreadDemo.java`)
   - Explains the difference between user threads (default) and daemon threads (background threads).
   - Highlights how the JVM terminates when only daemon threads remain.
   - Examples of daemon threads: Garbage Collector, monitoring tasks.

5. **Sequential Execution** (`SequantialDemoExecution.java`)
   - Demonstrates how to ensure threads execute sequentially using synchronization or `join()`.
   - Explains why thread execution order is not guaranteed without proper coordination.

## How to Use
- Each file contains detailed comments explaining the concepts and code.
- Run the examples to see how multithreading works in practice.
- Use this folder as a reference for understanding the basics of multithreading in Java.

## Best Practices
- Always use the `Runnable` interface for creating threads unless you need to override `Thread` methods.
- Use `join()` to ensure proper execution order when threads depend on each other.
- Be cautious with thread priorities, as they are not guaranteed to affect execution order.
- Use daemon threads for background tasks that should terminate when the application ends.
