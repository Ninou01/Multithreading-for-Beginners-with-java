package main.java.basicMultithreading;

public class SequantialDemoExecution {
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new ThreadA();
        Thread threadB = new ThreadB();
        threadA.start();
        threadB.start();
        threadA.join();
        System.out.println("Main thread is executing");
    }
}

// TODO: write readme files for the concepts learned

class ThreadA extends Thread {
    @Override
    public void run() {
        for (int i = 30; i < 50; i++) {
            System.out.println("Thread A: " + i);
        }
    }
}

class ThreadB extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 25; i++) {
            System.out.println("Thread B: " + i);
        }
    }
}
