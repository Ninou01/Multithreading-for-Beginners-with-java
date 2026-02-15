package main.java.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
===============================================
newSingleThreadExecutor: EXPLANATION
===============================================

WHAT IS newSingleThreadExecutor?
- `Executors.newSingleThreadExecutor()` creates an executor service with a single worker thread.
- Tasks submitted to this executor are executed sequentially, one at a time, in the order they are submitted.

HOW IT WORKS UNDER THE HOOD:
- The single-threaded executor uses a single worker thread to process tasks.
- Tasks are stored in an unbounded queue, and the worker thread pulls tasks from the queue one by one.
- If the worker thread dies unexpectedly, the executor creates a new thread to replace it.

WHY USE newSingleThreadExecutor?
- To ensure tasks are executed in the order they are submitted.
- To prevent concurrent execution of tasks when only one thread is needed.
- Useful for scenarios where task order matters (e.g., logging, sequential processing).

KEY POINTS:
1. **Sequential Execution**:
   - Tasks are guaranteed to execute one at a time in the order they are submitted.
   - Task 2 will not start until Task 1 finishes.

2. **Thread Reuse**:
   - The same thread is reused for all tasks, reducing the overhead of thread creation.

3. **Automatic Thread Management**:
   - The executor handles thread creation, reuse, and termination automatically.

4. **Graceful Shutdown**:
   - Use `service.shutdown()` or try-with-resources to ensure the executor shuts down properly.

WHEN TO USE:
- When you need to preserve the order of task execution.
- When tasks must not run concurrently.
- For lightweight, single-threaded task execution.

EXAMPLE USE CASES:
- Logging tasks in the order they occur.
- Processing tasks that depend on the results of previous tasks.
- Sequential file writing.

IMPORTANT NOTES:
- If a task takes too long to execute, subsequent tasks will be delayed.
- Use `newSingleThreadExecutor()` only when you need sequential execution. For parallel execution, consider other executor types like `newFixedThreadPool()`.

*/

public class SingleThreadExecutorDemo {
    public static void main(String[] args) {
        // Create a single-threaded executor
        // This ensures that tasks are executed sequentially (one at a time) in the order they are submitted
        try (ExecutorService service = Executors.newSingleThreadExecutor()) {
            
            // Submit 10 tasks to the executor
            for (int i = 0; i < 10; i++) {
                service.execute(new Task(i));  // Each task will be executed in order
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
