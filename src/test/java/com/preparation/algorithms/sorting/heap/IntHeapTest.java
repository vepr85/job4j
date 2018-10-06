package com.preparation.algorithms.sorting.heap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by abyakimenko on 07.10.2018.
 */
public class IntHeapTest {

    private static final Logger logger = LoggerFactory.getLogger(IntHeapTest.class);

    @Test
    public void sort() {
        IntHeap heapSort = new IntHeap();
        int[] sortedArray = heapSort.sort(new int[]{2, 15, 11, 10, 9, 8, 6, 0});
        int[] etalon = new int[]{0, 2, 6, 8, 9, 10, 11, 15};
        assertArrayEquals(etalon, sortedArray);

        heapSort = new IntHeap(IntHeap.HeapType.MAX);
        sortedArray = heapSort.sort(new int[]{2, 15, 11, 10, 9, 8, 6, 0});
        assertArrayEquals(etalon, sortedArray);
    }
}