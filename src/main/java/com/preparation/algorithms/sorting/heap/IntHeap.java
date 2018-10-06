package com.preparation.algorithms.sorting.heap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * https://www.youtube.com/watch?v=t0Cq6tVNRBA
 * <p>
 * Структура данных куча, которая сортирует элементы по возрастанию
 * <p>
 * Created by abyakimenko on 05.10.2018.
 */
public class IntHeap {
    private static final Logger logger = LoggerFactory.getLogger(IntHeap.class);
    private int size;
    private int capacity = 10;
    private int[] items = new int[capacity];
    private HeapType type;

    public enum HeapType {MAX, MIN}

    public IntHeap(HeapType type) {
        this.type = type;
    }

    public IntHeap() {
        type = HeapType.MIN;
    }

    public int[] sort(int[] ints) {
        int localSize = ints.length;
        int[] result = new int[localSize];
        // строим кучу
        for (final int item : ints) {
            add(item);
        }

        logger.info("{} heap: {}", type, items);
        if (useMinHeap()) {
            for (int i = 0; i < localSize; i++) {
                int polled = poll();
                result[i] = polled;
            }
        } else {
            for (int i = localSize - 1; i >= 0; i--) {
                int polled = poll();
                result[i] = polled;
            }
        }

        return result;
    }

    public int[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    /**
     * отдаёт рутовый элемент
     *
     * @return
     */
    public int peek() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        return items[0];
    }

    /**
     * отдаёт рутовый элемент, удаляет его из хипа и делает реорганизацию хипа
     *
     * @return
     */
    public int poll() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        int item = items[0];
        items[0] = items[size - 1];
        items[size - 1] = 0;
        size--;
        heapifyDown();
        return item;
    }

    public void add(int item) {
        ensureExtraCapacity();
        items[size] = item;
        size++;
        heapifyUp();
    }

    private void ensureExtraCapacity() {
        if (size == capacity) {
            items = Arrays.copyOf(items, capacity * 2);
            capacity *= 2;
        }
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index)
                && (useMinHeap() ? parent(index) > items[index] : parent(index) < items[index])) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    private boolean useMinHeap() {
        return type == HeapType.MIN;
    }

    private int parent(int childIndex) {
        return items[getParentIndex(childIndex)];
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int childIndex = getLeftChildIndex(index);

            int rightChild = rightChild(index);
            int leftChild = leftChild(index);
            if (hasRightChild(index)
                    && useMinHeap() ? rightChild < leftChild : rightChild > leftChild) {
                childIndex = getRightChildIndex(index);
            }

            int item = items[index];
            int childItem = items[childIndex];
            if (useMinHeap() ? item < childItem : item > childItem) {
                break;
            } else {
                swap(index, childIndex);
            }
            index = childIndex;
        }
    }

    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private int leftChild(int parentIndex) {
        return items[getLeftChildIndex(parentIndex)];
    }

    private int rightChild(int parentIndex) {
        return items[getRightChildIndex(parentIndex)];
    }

    private int getLeftChildIndex(int parentIndex) {
        return parentIndex * 2 + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return parentIndex * 2 + 2;
    }

    private boolean hasParent(int childIndex) {
        return parent(childIndex) >= 0;
    }

    private boolean hasLeftChild(int parentIndex) {
        return getLeftChildIndex(parentIndex) < size;
    }

    private boolean hasRightChild(int parentIndex) {
        return getRightChildIndex(parentIndex) < size;
    }

    private void swap(int parentIndex, int index) {
        int parent = items[parentIndex];
        items[parentIndex] = items[index];
        items[index] = parent;
    }
}
