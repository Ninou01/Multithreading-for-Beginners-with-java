package main.java.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
===============================================
newCachedThreadPool: EXPLANATION
===============================================

WHAT IS newCachedThreadPool?
- `Executors.newCachedThreadPool()` creates an executor service with a dynamically growing thread pool.
- Threads are created as needed to execute tasks, and idle threads are reused for new tasks.
- If a thread remains idle for 60 seconds, it is terminated and removed from the pool.

HOW IT WORKS UNDER THE HOOD:
1. When a task is submitted, the executor checks for an available idle thread.
2. If an idle thread is available, it is reused to execute the task.
3. If no idle threads are available, a new thread is created to handle the task.
4. Threads that remain idle for 60 seconds are terminated to free up resources.

WHY USE newCachedThreadPool?
- To handle a large number of short-lived tasks efficiently.
- To dynamically scale the number of threads based on the workload.
- Useful for applications with unpredictable or bursty workloads.

KEY POINTS:
1. **Dynamic Thread Creation**:
   - Threads are created as needed, with no upper limit on the number of threads.
   - This allows the executor to handle sudden spikes in workload.

2. **Thread Reuse**:
   - Idle threads are reused for new tasks, reducing the overhead of thread creation.

3. **Automatic Cleanup**:
   - Threads that remain idle for 60 seconds are terminated, preventing resource leaks.

4. **Graceful Shutdown**:
   - Use `service.shutdown()` or try-with-resources to ensure the executor shuts down properly.

WHEN TO USE:
- When the workload is unpredictable or bursty.
- For lightweight, short-lived tasks that can benefit from dynamic scaling.
- When you want to avoid the overhead of managing a fixed number of threads.

EXAMPLE USE CASES:
- Handling a large number of incoming requests in a web server.
- Executing tasks that arrive at irregular intervals.
- Processing tasks that complete quickly and do not require long-lived threads.

IMPORTANT NOTES:
- Be cautious with long-running tasks, as they can lead to unbounded thread creation and resource exhaustion.
- Use `newCachedThreadPool()` for workloads where tasks are short-lived and frequent.
- For workloads with a predictable number of tasks, consider `newFixedThreadPool()` instead.

REAL-WORLD ANALOGY:
- Think of a cached thread pool as a flexible team of freelancers:
  - New freelancers (threads) are hired as needed to handle tasks.
  - If no tasks are available, freelancers wait for 60 seconds before leaving.
  - Freelancers are reused for new tasks, reducing the cost of hiring and onboarding.

*/

public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        // Create a cached thread pool executor
        // This executor can create new threads as needed, and reuse previously constructed threads when available
        try (ExecutorService service = Executors.newCachedThreadPool()) {
            
            // Submit 10 tasks to the executor
            for (int i = 0; i < 10; i++) {
                service.execute(new Task(i)); 
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

