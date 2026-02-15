package main.java._01_basicMultithreading;

/*
===============================================
RUNNABLE vs THREAD CLASS
===============================================

TWO WAYS TO CREATE THREADS:
1. Implement Runnable interface ✓ PREFERRED
2. Extend Thread class

WHY RUNNABLE IS BETTER:
- Java has single inheritance (can't extend multiple classes)
- Runnable lets you extend other classes
- Better design: separates task from thread mechanism
- More flexible and reusable

WHEN TO USE THREAD CLASS:
- Need to override Thread methods (rare)
- Simple examples only

EXAM TIP: If asked "which is better?" → Answer: Runnable
Reason: Avoids single inheritance limitation

THREE WAYS TO CREATE RUNNABLE:
1. Named class (ThreadOne, ThreadTwo below)
2. Anonymous class: new Runnable() { public void run() {...} }
3. Lambda (Thread three below) - since Runnable is @FunctionalInterface

COMMON MISTAKE:
❌ thread.run() → runs in same thread (no multithreading!)
✓ thread.start() → creates new thread
*/

public class _01_RunnableThreadExample {
    public static void main(String[] args) {
        // Named class approach - good for reusable, complex logic
        Thread one = new Thread(new ThreadOne());
        Thread two = new Thread(new ThreadTwo());
        
        // Lambda approach - concise for simple tasks (Java 8+)
        Thread three = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread Three : " + i);
            }
        });
        
        one.start();   // Creates new thread, calls run()
        two.start();
        three.start();
    }
}

class ThreadOne implements Runnable {
    @Override
    public void run() {  // Must implement run() from Runnable
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread One : " + i);
        }
    }
}

class ThreadTwo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread Two : " + i);
        }
    }
}
