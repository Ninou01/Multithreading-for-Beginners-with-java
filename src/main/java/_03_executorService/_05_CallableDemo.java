package main.java._03_executorService;

import java.util.concurrent.*;

/*
 * ===============================================================
 *                  Callable & Future - Lesson Summary
 * ===============================================================
 *
 * 1) Callable Interface
 * ---------------------------------------------------------------
 * Callable<T> is similar to Runnable, but:
 *
 *   - It RETURNS a value
 *   - It can throw checked exceptions
 *
 * Method:
 *   T call() throws Exception;
 *
 * Example:
 *   Callable<Integer> task = () -> {
 *       return 5 * 2;
 *   };
 *
 * We submit a Callable to an ExecutorService.
 *
 *
 * 2) Submitting a Callable
 * ---------------------------------------------------------------
 * When submitting a Callable:
 *
 *   Future<T> future = executor.submit(callableTask);
 *
 * The submit() method returns a Future<T>.
 *
 *
 * 3) Future Interface
 * ---------------------------------------------------------------
 * Future<T> represents the result of an asynchronous computation.
 *
 * Think of it as:
 *   "A placeholder for a result that will be available later."
 *
 *
 * 4) Important Future Methods
 * ---------------------------------------------------------------
 *
 * A) get()
 * ---------------------------------------------------------------
 *   T result = future.get();
 *
 * - Blocks the calling thread until the task completes
 * - Returns the computed result
 * - Throws:
 *       InterruptedException
 *       ExecutionException (if call() threw an exception)
 *
 *
 * B) get(timeout, unit)
 * ---------------------------------------------------------------
 *   future.get(2, TimeUnit.SECONDS);
 *
 * - Waits only for the specified time
 * - Throws TimeoutException if task not finished
 *
 *
 * C) isDone()
 * ---------------------------------------------------------------
 *   future.isDone();
 *
 * - Returns true if:
 *     • Task completed successfully
 *     • Task threw an exception
 *     • Task was cancelled
 *
 *
 * D) cancel(boolean mayInterruptIfRunning)
 * ---------------------------------------------------------------
 *   future.cancel(true);
 *
 * - Attempts to cancel the task
 * - If true:
 *       The executing thread will be interrupted
 * - Returns true if cancellation succeeded
 *
 * Important:
 *   Cancellation works properly only if the task handles interruption
 *   (e.g., reacts to InterruptedException or checks isInterrupted()).
 *
 *
 * 5) Runnable vs Callable
 * ---------------------------------------------------------------
 * Runnable:
 *   - void run()
 *   - Cannot return value
 *   - Cannot throw checked exceptions
 *
 * Callable:
 *   - T call()
 *   - Returns value
 *   - Can throw checked exceptions
 *
 *
 * 6) Key Concept
 * ---------------------------------------------------------------
 * Callable + Future allow asynchronous computation:
 *
 *   - Task runs in another thread
 *   - Main thread can continue working
 *   - Result can be retrieved later using future.get()
 *
 * Blocking happens ONLY when get() is called.
 *
 *
 * Mental Model:
 * ---------------------------------------------------------------
 * Callable -> "Do work and give me a result."
 * Future   -> "Here is the result, come back later to get it."
 *
 * ===============================================================
 */


public class _05_CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        try(ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<Integer> result = executorService.submit(new ReturnValueTask());

            /*result.cancel(true);

            boolean cancelled = result.isCancelled();

            boolean done = result.isDone();*/

            System.out.println(result.get(6, TimeUnit.SECONDS));
            System.out.println("Main thread execution completed!");
        }
    }
}

class ReturnValueTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        return 12;
    }
}