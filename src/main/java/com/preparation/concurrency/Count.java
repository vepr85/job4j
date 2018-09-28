package com.preparation.concurrency;

// count - сделать атомиком или
// использовать synchronized на метод addOne + volatile на count
public class Count {
    private volatile int value;

    public void increment() {
        this.value++;
    }

    public int get() {
        return this.value;
    }
}