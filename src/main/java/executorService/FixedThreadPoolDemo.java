package main.java.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
===============================================
newFixedThreadPool: EXPLANATION
===============================================

WHAT IS newFixedThreadPool?
- `Executors.newFixedThreadPool(int nThreads)` creates an executor service with a fixed number of threads.
- The thread pool can execute up to `nThreads` tasks concurrently.
- If more tasks are submitted than there are threads, the extra tasks are queued and wait for a thread to become available.

HOW IT WORKS UNDER THE HOOD:
1. A fixed number of threads (`nThreads`) are created when the executor is initialized.
2. Tasks are submitted to the executor and stored in an unbounded queue.
3. The threads in the pool pull tasks from the queue and execute them.
4. Threads are reused for subsequent tasks, reducing the overhead of thread creation.

WHY USE newFixedThreadPool?
- To limit the number of threads running concurrently, which helps manage system resources.
- To reuse threads for multiple tasks, reducing the overhead of creating and destroying threads.
- Useful for scenarios where you know the maximum number of threads needed.

KEY POINTS:
1. **Concurrency Control**:
   - Limits the number of threads running at the same time to `nThreads`.
   - Prevents resource exhaustion caused by creating too many threads.

2. **Thread Reuse**:
   - Threads are reused for multiple tasks, improving performance.

3. **Task Queueing**:
   - If all threads are busy, additional tasks are queued and executed when a thread becomes available.

4. **Graceful Shutdown**:
   - Use `service.shutdown()` or try-with-resources to ensure the executor shuts down properly.

WHEN TO USE:
- When you need to limit the number of threads running concurrently.
- For CPU-bound or I/O-bound tasks where the number of threads should match the system's capacity.
- For predictable workloads where the number of threads can be determined in advance.

EXAMPLE USE CASES:
- Processing a fixed number of tasks concurrently (e.g., file uploads, database queries).
- Managing a pool of worker threads for a server application.
- Running tasks that require controlled concurrency.

IMPORTANT NOTES:
- If a thread dies unexpectedly, the executor replaces it with a new thread.
- Be cautious with long-running tasks, as they can block threads and delay other tasks.
- Use `newFixedThreadPool()` when you know the maximum number of threads needed. For dynamic workloads, consider `newCachedThreadPool()`.

REAL-WORLD ANALOGY:
- Think of a fixed thread pool as a team of workers (threads) at a factory:
  - The factory has a fixed number of workers (`nThreads`).
  - If all workers are busy, new tasks are queued until a worker becomes available.
  - Workers are reused for new tasks, reducing the cost of hiring and training new workers (thread creation).

*/

public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        // Create a fixed thread pool with 3 threads
        // This allows up to 3 tasks to be executed concurrently
        try (ExecutorService service = Executors.newFixedThreadPool(3)) {
            
            // Submit 10 tasks to the executor
            for (int i = 0; i < 10; i++) {
                service.execute(new Task(i));  // Tasks will be executed by the 3 threads in the pool
            }

        } catch (Exception e) {
            // Handle any exceptions that occur
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {
    private int taskId;

    Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        // Print the task ID and the thread executing it
        System.out.println("Task with id=" + this.taskId + " is being executed by thread: " + Thread.currentThread().getName());

        try {
            // Simulate some work by sleeping for 1 second
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
