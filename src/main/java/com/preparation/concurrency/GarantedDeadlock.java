package com.preparation.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Демонстрация гарантированного дедлока.
 * потоки блокируются на разных ресурсах (литеральная строка будет взята из пула строк)
 * после того, как 1 поток проскочит 1 блок synchronized (залочит "first") и уменьшит latch на 1, latch переведёт его
 * в состояние ожидания 2 поток залочит "second"
 */
public class GarantedDeadlock implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(GarantedDeadlock.class);
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
        logger.info("In run(): {}", Thread.currentThread().getName());
        try {
            synchronized (first) {
                logger.info("synchronized (first): {}", Thread.currentThread().getName());
                logger.info("latch: {}", latch.getCount());
                latch.countDown();
                latch.await();
                synchronized (second) {
                    logger.info("synchronized (second): {}", Thread.currentThread().getName());
                }
            }
        } catch (InterruptedException exception) {
            logger.error("Something goes wrong: {}", exception);
            // восстановим прерванное состояние потока
            Thread.currentThread().interrupt();
        }
    }
}