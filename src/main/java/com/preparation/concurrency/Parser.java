package com.preparation.concurrency;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Predicate;

// volatile - нет атомарности
// file - общий ресурс - есть проблема

// String -> StringBuilder, нет разделяемого ресурса - нет проблемы, можно использовать
// getContentWithoutUnicode и getContent copy paste - добавляем в качестве параметра Predicate
// класс сделан Immutable
// закрыты потоки с try with resources
// для работы с файлами использовать FileReader / Writer для решения проблем с кодировкой
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


/*
import java.io.*;

public class Parser {
    private File file;
    public synchronized void setFile(File f) {
        file = f;
    }
    public synchronized File getFile() {
        return file;
    }
    public String getContent() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }
    public String getContentWithoutUnicode() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }
    public void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}

ДЗ:
1) отрефакторить код Parser
2) написать многопоточные тесты
3) покачаться по вопросам в списке
4) написать гарантированный deadlock


// добавить 
public class Count {
    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public int get() {
        return this.value;
    }
}
* */


/*

 * MapStore.
 *
 * @author Aleksey Ivanov (mailto:tsrivanov@gmail.com).
 * @version $Id$
 * @since 0.1

1) getInstance - не нужен, и так есть INSTANCE!
2) private int id = 1; -  добавить volatile. this.id++ не атомарна
   может добавить synchronized?

public enum MapStore {
    INSTANCE;

    private final ConcurrentHashMap<Integer, JsonUser> store = new ConcurrentHashMap<>();
    private int id = 1;

    public static MapStore getInstance() {
        return INSTANCE;
    }

    public void add(JsonUser user) {
        this.store.put(this.id++, user);
    }

    public Collection<JsonUser> getAll() {
        return this.store.values();
    }
}
*/
















