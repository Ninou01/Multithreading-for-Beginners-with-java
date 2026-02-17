# Java Multithreading: Executor Service

This folder contains examples and lessons on the Executor Framework in Java, which provides a higher-level replacement for working with threads directly. The Executor Service manages thread pools and simplifies concurrent task execution.

## Topics Covered

1. **Single Thread Executor** (`_01_SingleThreadExecutorDemo.java`)
   - Creates an executor with a single worker thread.
   - Ensures tasks are executed sequentially in the order they are submitted.
   - **Use Case**: When task order must be preserved (e.g., logging, sequential file processing).
   - **Key Point**: Task 2 won't start until Task 1 completes.

2. **Fixed Thread Pool** (`_02_FixedThreadPoolDemo.java`)
   - Creates an executor with a fixed number of threads.
   - Limits concurrent execution to a specific number of threads.
   - Extra tasks are queued and executed when threads become available.
   - **Use Case**: CPU-bound or I/O-bound tasks with predictable concurrency needs.
   - **Key Point**: Prevents resource exhaustion by controlling the number of active threads.

3. **Cached Thread Pool** (`_03_CachedThreadPoolDemo.java`)
   - Creates an executor with a dynamically growing thread pool.
   - Threads are created as needed and reused for new tasks.
   - Idle threads are terminated after 60 seconds.
   - **Use Case**: Short-lived tasks with unpredictable or bursty workloads.
   - **Key Point**: Automatically scales with workload but can create unbounded threads if tasks are long-running.

4. **Scheduled Executor** (`_04_ScheduledExecutorDemo.java`)
   - Creates an executor that can schedule tasks to run after a delay or periodically.
   - Supports:
     - `schedule()`: Run once after a delay
     - `scheduleAtFixedRate()`: Run periodically with fixed rate
     - `scheduleWithFixedDelay()`: Run periodically with fixed delay between executions
   - **Use Case**: Background tasks, periodic monitoring, delayed execution.

5. **Callable and Future** (`_05_CallableDemo.java`)
   - Demonstrates `Callable<T>` interface, which returns a result (unlike `Runnable`).
   - `Future<T>` represents the result of an asynchronous computation.
   - Key methods:
     - `get()`: Blocks until result is available
     - `get(timeout, unit)`: Waits with timeout
     - `isDone()`: Checks if task completed
     - `cancel()`: Attempts to cancel the task
   - **Use Case**: When you need to retrieve a result from an asynchronous task.
