package com.preparation.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1) Реализуйте объект, который хранит в себе строку или ее представление. Объекту необходимо:
 * - содержать метод, который принимает на вход значение типа int, конвертирует его в строковое представление
 * (например, 4 -> "4"), а затем добавляет к концу строки.
 * - по требованию возвращать эту строку.
 * 2) Реализуйте 2 потока, которые в цикле добавляют всегда одно и то же число (1-й поток число 1, второй поток число 2)
 * в строку из пункта 1.
 * <p>
 * Работа потоков должна быть организована таким образом, чтобы числа добавлялись в строку в следующем порядке:
 * сначала 10 чисел из первого потока, затем 10 чисел из второго, затем снова 10 чисел из первого и так далее.
 * <p>
 * Created by abyakimenko on 28.09.2018.
 */
public class StringObject {
    private static final Logger logger = LoggerFactory.getLogger(StringObject.class);

    private volatile String text;
    private static final int SET_SIZE = 10;
    private final int iterations;
    private int lastNum = 2;
    private int lastLen = 0;

    public StringObject(int iterations) {
        this.iterations = iterations;
        text = "";
    }

    synchronized void addInt(int number) {

        while (lastNum == number) {
            try {
                logger.info("{} will wait....", Thread.currentThread().getName());
                wait(100);
            } catch (InterruptedException e) {
                logger.error("InterruptedException exception has occured");
                Thread.currentThread().interrupt();
            }
        }

        text = text + String.valueOf(number);
        int len = text.length() / SET_SIZE;
        if (len != lastLen || ((text.length() - iterations) == (iterations / SET_SIZE) * SET_SIZE)) {
            lastNum = number;
            lastLen = len;
            notifyAll();
        }
    }

    String value() {
        return text;
    }
}
