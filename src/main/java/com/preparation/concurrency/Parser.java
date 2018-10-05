package com.preparation.concurrency;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * volatile - нет атомарности
 * file - общий ресурс - есть проблема
 * <p>
 * String -> StringBuilder, нет разделяемого ресурса - нет проблемы, можно использовать
 * getContentWithoutUnicode и getContent copy paste - добавляем в качестве параметра Predicate
 * класс сделан Immutable
 * закрыты потоки с try with resources
 * для работы с файлами использовать FileReader / Writer для решения проблем с кодировкой
 **/
public final class Parser {

    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    // отличие только 1 - можем применить предикат
    public String getContent(Predicate<Integer> condition) throws IOException {
        StringBuilder output = new StringBuilder();
        try (FileReader fr = new FileReader(file)) {
            int data;
            while ((data = fr.read()) > 0) {
                if (condition.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public void saveContent(String content) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                fw.write(content.charAt(i));
            }
        }
    }
}