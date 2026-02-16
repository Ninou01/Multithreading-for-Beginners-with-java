package main.java._03_executorService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * ===============================================================
 *                   newScheduledThreadPool
 * ===============================================================
 *
 * 1) What is newScheduledThreadPool?
 * ---------------------------------------------------------------
 * Executors.newScheduledThreadPool(n) creates a ScheduledExecutorService
 * with a pool of n threads.
 *
 * It allows us to:
 *   - Execute a task once after a delay
 *   - Execute a task periodically
 *
 * It is used for time-based task scheduling.
 *
 *
 * 2) Why Use It?
 * ---------------------------------------------------------------
 * It is preferred over:
 *   - Thread.sleep()
 *   - Timer (older and less flexible)
 *
 * Advantages:
 *   - Supports multiple threads
 *   - More reliable
 *   - Production-ready
 *   - Better error handling than Timer
 *
 *
 * 3) Main Scheduling Methods
 * ---------------------------------------------------------------
 *
 * A) schedule()
 * ---------------------------------------------------------------
 * Runs a task once after a given delay.
 *
 * Example:
 *   executor.schedule(task, 5, TimeUnit.SECONDS);
 *
 * Behavior:
 *   - Waits 5 seconds
 *   - Executes once
 *
 *
 * B) scheduleAtFixedRate()
 * ---------------------------------------------------------------
 * Runs a task repeatedly at a fixed rate.
 *
 * Parameters:
 *   (task, initialDelay, period, TimeUnit)
 *
 * Behavior:
 *   - Execution time is based on START time
 *   - Maintains a fixed schedule (clock-based)
 *
 * Example timeline (period = 5s):
 *   0s → 5s → 10s → 15s → 20s ...
 *
 * Think:
 *   "Run every X seconds according to the clock."
 *
 *
 * C) scheduleWithFixedDelay()
 * ---------------------------------------------------------------
 * Runs a task repeatedly with a delay AFTER task completion.
 *
 * Behavior:
 *   - Task runs
 *   - Task finishes
 *   - Waits X seconds
 *   - Runs again
 *
 * Think:
 *   "Finish → wait → run again"
 *
 *
 * 4) Key Difference
 * ---------------------------------------------------------------
 * scheduleAtFixedRate:
 *   - Fixed interval between START times
 *   - Clock-based scheduling
 *
 * scheduleWithFixedDelay:
 *   - Fixed delay between END of one execution
 *     and START of the next
 *   - Rest-based scheduling
 *
 *
 * 5) Thread Pool Behavior
 * ---------------------------------------------------------------
 * If pool size = n:
 *   - Up to n tasks can run in parallel
 *   - Different scheduled tasks may run concurrently
 *
 *
 * 6) Shutdown Methods
 * ---------------------------------------------------------------
 *
 * A) shutdown()
 *   - Stops accepting new tasks
 *   - Already submitted tasks continue executing
 *   - Graceful shutdown
 *
 * B) shutdownNow()
 *   - Attempts to stop all actively executing tasks
 *   - Cancels waiting tasks
 *   - Interrupts running threads
 *   - Returns a list of tasks that never started
 *
 * Note:
 *   shutdownNow() does NOT guarantee immediate stop.
 *   Tasks must properly handle interruption (check
 *   Thread.currentThread().isInterrupted() or handle
 *   InterruptedException).
 *
 *
 * Mental Model:
 * ---------------------------------------------------------------
 * newScheduledThreadPool is like a smart scheduler with multiple
 * workers that can:
 *   - Run tasks later
 *   - Run tasks repeatedly
 *   - Manage execution timing automatically
 *
 * shutdown()  -> polite stop
 * shutdownNow() -> forceful stop (with interruption)
 *
 * ===============================================================
 */


public class _04_ScheduledExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new ProbeTask(), 1000, 2000, TimeUnit.MILLISECONDS);

        try {
            if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}

class ProbeTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Probing end point for updates...");
    }
}