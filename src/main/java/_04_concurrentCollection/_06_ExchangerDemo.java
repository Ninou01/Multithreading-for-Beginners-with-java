package main.java._04_concurrentCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/*
 * ===============================================
 *              UNDERSTANDING Exchanger
 * ===============================================
 *
 * 1) What is Exchanger?
 * ------------------------------------------------
 * Exchanger<T> is a synchronization tool that allows
 * two threads to meet and swap objects.
 *
 * It is designed specifically for pairwise data exchange.
 *
 *
 * 2) Core Concept
 * ------------------------------------------------
 * Two threads call:
 *
 *     exchanger.exchange(data);
 *
 * Each thread:
 *     - Provides an object
 *     - Blocks until another thread arrives
 *     - Receives the other thread’s object
 *
 * The exchange happens atomically.
 *
 *
 * 3) Important Properties
 * ------------------------------------------------
 * - Exactly two threads participate per exchange.
 * - If only one thread calls exchange(), it blocks
 *   until another thread arrives.
 * - No data copying occurs — only object references
 *   are swapped.
 *
 *
 * 4) Typical Use Case
 * ------------------------------------------------
 * Exchanger is commonly used in two-thread pipelines
 * where buffers need to be swapped efficiently.
 *
 * Example scenario:
 *     - Thread A fills a buffer with data.
 *     - Thread B processes that buffer.
 *     - They exchange buffers instead of copying data.
 *
 * This pattern is known as "double buffering".
 *
 *
 * 5) Why Use Exchanger?
 * ------------------------------------------------
 * - Avoids manual synchronization.
 * - Avoids heavy locking.
 * - Avoids copying large data structures.
 * - Very efficient for two-thread handoff.
 *
 *
 * 6) Difference From Other Concurrency Tools
 * ------------------------------------------------
 * CountDownLatch → Wait for tasks to finish.
 * CyclicBarrier  → Group synchronization point.
 * BlockingQueue  → Producer-consumer (many threads).
 * Exchanger      → Direct swap between exactly 2 threads.
 *
 *
 * 7) When NOT To Use Exchanger
 * ------------------------------------------------
 * - When more than 2 threads are involved.
 * - When you need a queue or buffering system.
 * - When communication is not strictly pair-based.
 *
 * ===============================================
 */

public class _06_ExchangerDemo { // log batching (producer collects logs, consumer writes to disk/network)

    private static final int BATCH_SIZE = 5;
    private static final int BATCHES = 4;

    public static void main(String[] args) {
        Exchanger<List<String>> exchanger = new Exchanger<>();

        Thread collector = new Thread(() -> {
            List<String> buffer = new ArrayList<>(BATCH_SIZE);

            try {
                for (int b = 1; b <= BATCHES; b++) {
                    // Simulate incoming logs (from your app)
                    while (buffer.size() < BATCH_SIZE) {
                        String log = "INFO  requestId=" + b + "-" + buffer.size() + " message=Order confirmed";
                        buffer.add(log);
                        // Simulate logs arriving fast
                        Thread.sleep(80);
                    }

                    System.out.println("[Collector] Batch ready (" + buffer.size() + " logs). Exchanging...");
                    buffer = exchanger.exchange(buffer); // give full buffer, receive empty buffer
                    System.out.println("[Collector] Got empty buffer, continue collecting...");
                }

                // Send an empty buffer one last time as "shutdown signal"
                buffer.clear();
                exchanger.exchange(buffer);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread writer = new Thread(() -> {
            List<String> buffer = new ArrayList<>(BATCH_SIZE);

            try {
                while (true) {
                    buffer = exchanger.exchange(buffer); // receive full buffer, give empty buffer

                    // shutdown signal: empty batch
                    if (buffer.isEmpty()) {
                        System.out.println("[Writer] Shutdown signal received.");
                        break;
                    }

                    // "Real-life" action: write logs in batch
                    flushToDiskOrRemote(buffer);

                    // Important: writer clears buffer before giving it back next exchange
                    buffer.clear();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        collector.start();
        writer.start();
    }

    private static void flushToDiskOrRemote(List<String> batch) {
        // Imagine this is writing to a file, database, Kafka, ELK, etc.
        System.out.println("[Writer] Flushing " + batch.size() + " logs...");
        for (String log : batch) {
            System.out.println("   " + log);
        }

        // Simulate slow IO
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Writer] Flush complete.\n");
    }
}