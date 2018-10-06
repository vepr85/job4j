package com.preparation.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Классический случай deadlock
 * Решением является блокировка ресурсов каждый потом в одинаковой последовательности
 */
public class ResolveDeadLockTest {

    private static final Logger logger = LoggerFactory.getLogger(ResolveDeadLockTest.class);

    public static void main(String[] args) {
        ResolveDeadLockTest test = new ResolveDeadLockTest();

        final A a = test.new A();
        final B b = test.new B();

        // Thread-1
        Runnable block1 = () -> {
            synchronized (a) {
                try {
                    // Добавляем задержку, чтобы обе нити могли начать попытки
                    // блокирования ресурсов
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException");
                    Thread.currentThread().interrupt();
                }
                // Thread-1 заняла A но также нуждается в B
                synchronized (b) {
                    logger.info("In block 1");
                }
            }
        };

        // Thread-2
        Runnable block2 = () -> {
            synchronized (a) {
                // Thread-2 заняла B но также нуждается в A
                synchronized (b) {
                    logger.info("In block 2");
                }
            }
        };

        new Thread(block1).start();
        new Thread(block2).start();
    }

    // Resource A
    private class A {
        private int i = 10;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    // Resource B
    private class B {
        private int i = 20;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }
}