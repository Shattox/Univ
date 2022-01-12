package fr.umlv.set;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntConsumer;

public class IntHashSet {
    private record Entry(int value, Entry next) {
    }

    private final Entry[] entries;
    private int size;

    public IntHashSet() {
        this.entries = new Entry[8];
    }

    private int hash(int value) {
        return value & (entries.length - 1);
    }

    public void add(int value) {
        var index = hash(value);
        var first = entries[index];

        for (var entry = first; entry != null; entry = entry.next) {
            if (entry.value == value) {
                return;
            }
        }
        entries[index] = new Entry(value, first);
        size ++;
    }

    public int size() {
        return size;
    }

    public void forEach(IntConsumer consumer) {
        Objects.requireNonNull(consumer);
        Arrays.stream(entries).forEach(first -> {
            for (var entry = first; entry != null; entry = entry.next) {
                consumer.accept(entry.value);
            }
        });
    }

    public boolean contains(int value) {
        var index = hash(value);
        var first = entries[index];

        for (var entry = first; entry != null; entry = entry.next) {
            if (entry.value == value) {
                return true;
            }
        }
        return false;
    }
}
