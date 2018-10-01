package com.preparation.solving;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * От банка поступил заказ разработать алгоритм подсчета временных интервалов, когда в банке было максимальное
 * число посетителей.
 * <p>
 * Банк начинает работать с 8:00 по 20:00.
 * <p>
 * У каждого посетителя есть время когда он пришел и когда он ушел.
 * <p>
 * Время фиксируется в миллисекундах.
 * <p>
 * Необходимо разработать алгоритм, который принимает список всех посещений и выдает интервалы времени, когда в банке
 * было максимальное количество посетителей.
 */
public class Bank {

    public static class Visit {
        private final long in;
        private final long out;

        public Visit(final long in, final long out) {
            this.in = in;
            this.out = out;
        }

        public String getTime() {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(in);
            String inStr = String.format("%s:%s", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
            cal.setTimeInMillis(out);
            String outStr = String.format("%s:%s", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));

            return String.format("[In: %s] [Out: %s]", inStr, outStr);
        }
    }

    public static class Info {
        private long max;
        private long start;
        private long end;

        public Info() {
            this(0, 0, 0);
        }

        public Info(long max, long start, long end) {
            this.max = max;
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Info info = (Info) o;

            if (max != info.max) return false;
            if (start != info.start) return false;
            return end == info.end;
        }

        @Override
        public int hashCode() {
            int result = (int) (max ^ (max >>> 32));
            result = 31 * result + (int) (start ^ (start >>> 32));
            result = 31 * result + (int) (end ^ (end >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "max=" + max +
                    ", start=" + this.toTime(this.start) +
                    ", end=" + this.toTime(this.end) +
                    '}';
        }

        public String toTime(long time) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            return String.format("%s:%s", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
        }
    }

    public List<Info> max(List<Visit> visits) {
        if (visits.size() == 1) {
            Visit viz = visits.get(0);
            return Collections.singletonList(new Info(1, viz.in, viz.out));
        }

        List<Visit> sorted = visits.stream().sorted(Comparator.comparing(item -> item.in)).collect(toList());
        Predicate<Integer> last = v -> sorted.size() == (v + 1);
        Map<Info, Visit> tmpMap = new LinkedHashMap<>();

        Visit accumm = sorted.get(0);
        int tmpCounter = 1;
        boolean prevApplied = false;
        for (int i = 0; i < sorted.size(); i++) {
            Visit vizCurrent = sorted.get(i);
            accumm = Objects.isNull(accumm) && !last.test(i) ? vizCurrent : accumm;

            if (last.test(i)) {

                if (prevApplied) {
                    tmpMap.put(new Info(tmpCounter, accumm.in, accumm.out), accumm);
                    break;
                }

                if (intersect(vizCurrent, accumm)) {
                    tmpCounter++;
                    accumm = accumVizit(accumm, vizCurrent);
                } else {
                    accumm = vizCurrent;
                }

                tmpMap.put(new Info(tmpCounter, accumm.in, accumm.out), accumm);
                break;
            }

            Visit vizNext = sorted.get(i + 1);
            if (intersect(vizCurrent, vizNext)) {
                tmpCounter++;
                accumm = accumVizit(accumm, vizNext);
                prevApplied = true;
            } else {
                tmpMap.put(new Info(tmpCounter, accumm.in, accumm.out), accumm);
                tmpCounter = 1;
                accumm = null;
                prevApplied = false;
            }
        }

        return new ArrayList<>(tmpMap.keySet());
    }

    private boolean intersect(Visit vizCurrent, Visit vizNext) {
        if (Objects.isNull(vizCurrent) || Objects.isNull(vizNext)) {
            return false;
        }
        return vizCurrent.in <= vizNext.in && vizCurrent.out >= vizNext.in && vizNext.out >= vizCurrent.in;
    }

    private Visit accumVizit(Visit vizCurrent, Visit vizNext) {
        return new Visit(vizNext.in, (vizCurrent.out < vizNext.out) ? vizCurrent.out : vizNext.out);
    }
}


















