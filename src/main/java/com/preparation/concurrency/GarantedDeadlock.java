package com.preparation.concurrency;

import java.util.concurrent.CountDownLatch;

public class GarantedDeadlock implements Runnable {
    private final Object first;
    private final Object second;
    private static final CountDownLatch latch = new CountDownLatch(2);

    public GarantedDeadlock(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public static void main(String[] args) {
        new Thread(new GarantedDeadlock("first", "second")).start();
        new Thread(new GarantedDeadlock("second", "first")).start();
    }

    @Override
    public void run() {
        System.out.println("In run(): " + Thread.currentThread().getName());
        synchronized (first) {
            System.out.println("synchronized (first): " + Thread.currentThread().getName());
            try {
                System.out.println("latch: " + latch.getCount());
                latch.countDown();
                latch.await();
            } catch (InterruptedException ignored) {
            }
            
            synchronized (second) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}