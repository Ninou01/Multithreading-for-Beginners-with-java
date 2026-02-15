package main.java.basicMultithreading;

/*
===============================================
DAEMON vs USER THREADS - EXAM NOTES
===============================================

TWO TYPES OF THREADS IN JAVA:
1. User Threads (Normal Threads) - DEFAULT
2. Daemon Threads (Background Threads)

USER THREADS:
- Default type when creating threads
- JVM waits for ALL user threads to complete before terminating
- Used for main application logic
- Examples: main thread, processing threads

DAEMON THREADS:
- Background service threads
- JVM does NOT wait for daemon threads to complete
- Automatically terminated when all user threads finish
- Used for supporting tasks
- Examples: Garbage Collector, monitoring, cleanup tasks

KEY DIFFERENCE:
User Thread: JVM waits → Application keeps running
Daemon Thread: JVM ignores → Killed when user threads end

HOW TO CREATE DAEMON THREAD:
thread.setDaemon(true);  // MUST call BEFORE start()

Common Mistakes:
❌ thread.start(); thread.setDaemon(true); → IllegalThreadStateException
✓ thread.setDaemon(true); thread.start(); → Correct order

CHECK IF DAEMON:
boolean isDaemon = thread.isDaemon();

EXAM SCENARIO:
If only daemon threads remain → JVM exits immediately
If any user thread exists → JVM continues running
*/

public class DaemonUserThreadDemo {
    public static void main(String[] args) {
        // Create background daemon thread
        Thread bgThread = new Thread(new DaemonHelper());
        
        // Create normal user thread
        Thread usrThread = new Thread(new UserThreadHelper());
        
        // Set daemon BEFORE starting (important!)
        bgThread.setDaemon(true);  // Background service thread
        
        // Start both threads
        System.out.println("daemon thread priority: " + bgThread.getPriority());
        System.out.println("user thread priority: " + usrThread.getPriority());
        bgThread.start();   // Daemon: runs 500 loops (but won't complete)
        usrThread.start();  // User: runs 5 seconds then exits
        
        /*
        EXECUTION FLOW:
        1. Daemon thread starts (infinite-like loop for 500 iterations)
        2. User thread starts (runs for 5 seconds)
        3. User thread completes after 5 seconds
        4. JVM sees NO user threads remaining
        5. JVM terminates → daemon thread is KILLED mid-execution
        
        RESULT: Daemon thread prints ~5 times, NOT 500 times!
        */
    }
}

// Daemon thread - background helper task
class DaemonHelper implements Runnable {
    @Override
    public void run() {
        int count = 0;
        while (count < 500) {  // Would take 500 seconds to complete
            try {
                Thread.sleep(1000);  // Sleep 1 second per iteration
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
            System.out.println("Daemon helper running ...");
        }
        // This line likely NEVER executes - thread killed by JVM
    }
}

// User thread - main application task
class UserThreadHelper implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);  // Sleep 5 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User Thread Helper done with execution!");
        // After this, no user threads remain → JVM exits → daemon dies
    }
}

