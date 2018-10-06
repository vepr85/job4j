package com.preparation.algorithms.sorting.heap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сортировка кучей.
 * <p>
 * 1) постороение структуры heap (max) путём перестановки элементов массива.
 * 2) Перестановка корневого элемента в конец массива и "удаление" его из кучи
 * (отсортированный элемент больше не трогается)
 * 3) заново строим кучу из осташихся элементов
 * <p>
 * Created by abyakimenko on 05.10.2018.
 */
public class heapSort {
    private static final Logger logger = LoggerFactory.getLogger(heapSort.class);

    public static void main(String[] args) {
        int[] inArray = new int[]{2, 15, 11, 10, 9, 8, 6, 0};
        sort(inArray);
    }

    static void sort(int[] array) {
        // строим кучу (максимальную)
        int size = array.length;
        buildMaxHeap(array, size);
        logger.info("Max heap's been built: {}", array);

        // извлекаем элементы с корня кучи и перестраиваем её каждый раз
        for (int i = size - 1; i >= 0; i--) {
            // перемещаем текущий корень в конец кучи
            swap(array, 0, size - 1);
            --size;
            // строим кучу заново
            buildMaxHeap(array, size);
        }

        logger.info("Sorted array: {}", array);
    }

    private static void buildMaxHeap(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(array, i, size);
        }
    }

    static void swap(int[] array, int from, int to) {
        int tmp = array[from];
        array[from] = array[to];
        array[to] = tmp;
    }

    static void heapify(int[] array, int parentIndex, int size) {
        int minIdx = parentIndex;

        while (parentIndex < size) {
            int leftIdx = 2 * parentIndex + 1;
            int rightIdx = 2 * parentIndex + 2;
            boolean hasLeftChild = leftIdx < size;
            boolean hasRightChild = rightIdx < size;

            if (hasLeftChild && array[leftIdx] < array[minIdx]) {
                minIdx = leftIdx;
            }
            if (hasRightChild && array[rightIdx] < array[minIdx]) {
                minIdx = rightIdx;
            }

            if (minIdx == parentIndex) {
                return;
            }

            swap(array, minIdx, parentIndex);

            parentIndex = minIdx;
        }
    }
}
