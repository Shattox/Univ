package fr.umlv.set;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class DynamicHashSet<E> {
    private record Entry<T>(T value, Entry<T> next) {
    }

    private Entry<E>[] entries;
    private int size;

    public DynamicHashSet() {
        this.entries = (Entry<E>[]) new Entry<?>[8];
    }

    private int hash(int value) {
        return value & (entries.length - 1);
    }

    public void add(E value) {
        var index = hash(value.hashCode());
        var first = entries[index];

        for (var entry = first; entry != null; entry = entry.next) {
            if (entry.value.equals(value)) {
                return;
            }
        }
        if (entries.length / 2 < size) {
            var newEntries = (Entry<E>[]) new Entry<?>[entries.length * 2];
            for (var entry: entries) {
                for (var entryElem = entry; entryElem != null; entryElem = entryElem.next) {
                    newEntries[hash(entry.value.hashCode())] = new Entry<E>(entryElem.value, first);
                }
            }
            entries = newEntries;
        }
        entries[index] = new Entry<E>(value, first);
        size++;
    }

    public int size() {
        return size;
    }

    public void forEach(Consumer<E> consumer) {
        Objects.requireNonNull(consumer);
        Arrays.stream(entries).forEach(first -> {
            for (var entry = first; entry != null; entry = entry.next) {
                consumer.accept(entry.value);
            }
        });
    }

    public boolean contains(Object value) {
        var index = hash(value.hashCode());
        var first = entries[index];

        for (var entry = first; entry != null; entry = entry.next) {
            if (entry.value.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
