package com.preparation.concurrency;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Repeatable;

import static org.junit.Assert.assertEquals;

/**
 * Created by abyakimenko on 26.09.2018.
 */
@RunWith(ConcurrentTestRunner.class)
public class ParserTest {

    private Parser parser;
    private final static int THREAD_COUNT = 5;

    @Before
    public void setUp() {
        parser = new Parser(new File("D:\\Foo.java"));
    }

    @Test
    @ThreadCount(THREAD_COUNT)
    public void should_saveContentThreadSafe() throws IOException {
        parser.saveContent("SUPER STRINGУФХЦ");
    }

    @After
    public void testResult() throws IOException {
        assertEquals("SUPER STRINGУФХЦ", parser.getContent((data) -> true));
        assertEquals("SUPER STRING", parser.getContent((data) -> data < 0x80));
    }
}