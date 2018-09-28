package com.preparation.concurrency;

import java.util.concurrent.CountDownLatch;

public class Deadlock implements Runnable {
    private final Object first;
    private final Object second;
    private static final CountDownLatch latch = new CountDownLatch(2);

    public Deadlock(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public static void main(String[] args) {
        new Thread(new Deadlock("first", "second")).start();
        new Thread(new Deadlock("second", "first")).start();
    }

    @Override
    public void run() {
        synchronized (first) {
            latch.countDown();
            try {
                latch.await();
                Thread.interrupted();
            } catch (InterruptedException ignored) {
            }
            synchronized (second) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}