package com.preparation.concurrency;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * Created by abyakimenko on 28.09.2018.
 */
public class StringObjectTest {

    private StringObject stringObject;

    @Before
    public void setUp() throws Exception {
        stringObject = new StringObject();
    }

    @Test
    public void shouldAddIntOneThread() {
        stringObject.addInt(1);
        stringObject.addInt(2);
        stringObject.addInt(3);
        stringObject.addInt(4);

        assertEquals("1234", stringObject.value());
    }

    @Test
    public void shouldAddIntMultiThreads() {
        final CountDownLatch START = new CountDownLatch(2);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                stringObject.addInt(1);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                stringObject.addInt(2);
            }
        });


        t1.start();
        t2.start();


        String substr = stringObject.value().substring(0, 10);
        assertEquals("1111111111", substr);
    }
}