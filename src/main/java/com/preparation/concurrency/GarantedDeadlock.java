package com.preparation.concurrency;

// + http://qaru.site/questions/2383790/how-to-write-guaranteed-deadlock-via-waitnotify

// Написать гарантированный deadlock
// объяснить принцип работы
public class GarantedDeadlock implements Runnable {

    private Object lock1;
    private Object lock2;

    public GarantedDeadlock(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock1) {
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }
    }

    public static void main(String a[]) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(new GarantedDeadlock(lock1, lock2), "FIRST").start();
        new Thread(new GarantedDeadlock(lock2, lock1), "SECOND").start();
    }
}