package main.java._04_concurrentCollection;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/*
 * ===============================================
 *            UNDERSTANDING CyclicBarrier
 * ===============================================
 *
 * 1) What is CyclicBarrier?
 * ------------------------------------------------
 * CyclicBarrier is a synchronization aid that allows
 * a fixed number of threads to wait for each other
 * at a common point (called the barrier).
 *
 * When the specified number of threads call await(),
 * all of them are released simultaneously.
 *
 *
 * 2) Why is it called "Cyclic"?
 * ------------------------------------------------
 * Because after all threads reach the barrier and
 * are released, the barrier automatically resets.
 *
 * It can be reused for multiple phases of work.
 *
 *
 * 3) Basic Usage
 * ------------------------------------------------
 * CyclicBarrier barrier = new CyclicBarrier(n);
 *
 * - n = number of threads that must meet
 *
 * Each thread:
 *     barrier.await();
 *
 * When the nth thread calls await():
 *     - All waiting threads are released
 *     - The barrier resets automatically
 *
 *
 * 4) Barrier Action (Optional)
 * ------------------------------------------------
 * You can provide a Runnable action that runs
 * once when the last thread reaches the barrier.
 *
 * Example:
 *     new CyclicBarrier(n, () -> {
 *         // code executed once per cycle
 *     });
 *
 * Important:
 * The barrier action is executed by the LAST
 * thread that reaches the barrier.
 *
 *
 * 5) What Problem Does It Solve?
 * ------------------------------------------------
 * It is used when:
 *     - Work is divided into phases
 *     - All threads must complete Phase 1
 *       before starting Phase 2
 *
 * Example:
 *     Phase 1 → all threads compute part of data
 *     Barrier
 *     Phase 2 → all threads continue together
 *
 *
 * 6) Difference From CountDownLatch
 * ------------------------------------------------
 * CountDownLatch:
 *     - One-time use
 *     - One thread waits for others
 *
 * CyclicBarrier:
 *     - Reusable
 *     - All threads wait for each other
 *
 *
 * 7) Important Notes
 * ------------------------------------------------
 * - If one thread fails or is interrupted,
 *   the barrier becomes broken.
 * - Other waiting threads will receive
 *   BrokenBarrierException.
 *
 *
 * 8) Mental Model
 * ------------------------------------------------
 * Think of it as a meeting point:
 *
 *     All threads arrive → door opens →
 *     they continue together →
 *     door resets for next meeting.
 *
 * ===============================================
 */

public class _05_CyclicBarrierDemo {
    public static final int[][] matrix = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    public static final int[] columnSums = new int[3];

    public static void main(String[] args) throws InterruptedException {

        // Phase 1 completion signal (rows squared)
        CountDownLatch squaredDone = new CountDownLatch(matrix.length);

        // Barrier for "all columns computed" + final action
        CyclicBarrier columnSumBarrier = new CyclicBarrier(matrix[0].length, () -> {
            System.out.println("All columns computed. Column sums: " + Arrays.toString(columnSums));
            int total = Arrays.stream(columnSums).sum();
            System.out.println("Final total (sum of column sums): " + total);
        });

        // -------- Phase 1: square rows (one thread per row) --------
        for (int r = 0; r < matrix.length; r++) {
            final int row = r;
            new Thread(() -> squareRow(row, squaredDone)).start();
        }

        // Wait until ALL rows are squared before starting column workers
        squaredDone.await();

        // -------- Phase 2: sum columns (one thread per column) --------
        for (int c = 0; c < matrix[0].length; c++) {
            final int col = c;
            new Thread(() -> sumColumn(col, columnSumBarrier)).start();
        }
    }

    public static void squareRow(int row, CountDownLatch squaredDone) {
        try {
            for (int i = 0; i < matrix[row].length; i++) {
                matrix[row][i] = matrix[row][i] * matrix[row][i];
            }
            System.out.println("Row " + row + " squared: " + Arrays.toString(matrix[row]));
        } finally {
            // Always signal completion even if an exception happens
            squaredDone.countDown();
        }
    }

    public static void sumColumn(int col, CyclicBarrier barrier) {
        try {
            int sum = 0;
            for (int r = 0; r < matrix.length; r++) {
                sum += matrix[r][col];
            }

            columnSums[col] = sum;
            System.out.println("Column " + col + " sum: " + sum);

            // Wait until all columns are computed, then barrier action runs
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
