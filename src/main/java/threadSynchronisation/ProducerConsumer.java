package main.java.threadSynchronisation;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        Worker worker = new Worker(0, 5);
        
        Thread producerThread = new Thread(() -> {
            try {
                worker.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        Thread consumerThread = new Thread(() -> {
            try {
                worker.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producerThread.start();
        consumerThread.start();
    }
    
}

class Worker {
    private int count = 0;
    private final int bottom;
    private final int top;
    private final List<Integer> container = new ArrayList<>();
    private final Object lock = new Object();

    public Worker(int bottom, int top) {
        this.bottom = bottom;
        this.top = top;
    }

    public void producer() throws InterruptedException {
        synchronized(lock) {
            while (true) {
                if (container.size() == top) {
                    System.out.println("Container is full");
                    lock.wait();
                } else {
                    count++;
                    container.add(count);
                    System.out.println("Produced: " + count);
                    lock.notifyAll();
                }
                Thread.sleep(1000);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized(lock) {
            while (true) {
                if (container.size() == bottom) {
                    System.out.println("Container is empty");
                    lock.wait();
                } else {
                    System.out.println("Consumed: " + container.removeFirst());
                    lock.notifyAll();
                }
                Thread.sleep(1000);
            }
        }
    }

}
