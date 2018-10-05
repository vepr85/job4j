package com.preparation.concurrency;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by abyakimenko on 28.09.2018.
 */
public class CountTest {

    private Count counter = new Count();

    @Test
    public void increment() throws InterruptedException {
        counter.increment();
    }

    @After
    public void should_count4() throws Exception {
        assertEquals("4 Default Threads running addOne in parallel should lead to 4", 4,
                counter.get());
    }
}