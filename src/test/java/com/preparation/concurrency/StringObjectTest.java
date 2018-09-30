package com.preparation.concurrency;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by abyakimenko on 28.09.2018.
 */
public class StringObjectTest {

    @Test
    public void shouldAddIntMultiThreads() throws InterruptedException {
        int iterations = 20;
        StringObject stringObject = new StringObject(iterations);

        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 2; i++) {
            final int finalI = i;
            Runnable runner = () -> {
                for (int j = 0; j < iterations; j++) {
                    stringObject.addInt(finalI);
                }
            };
            service.submit(runner);
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        String value = stringObject.value();
        String substr1 = value.substring(0, 10);
        String substr2 = value.substring(value.length() - 10, value.length());
        assertEquals("1111111111", substr1);
        assertEquals("2222222222", substr2);
    }
}