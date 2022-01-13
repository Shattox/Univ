package fr.umlv.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class Fifo<E> implements Iterable<E> {
    private final E[] array;
    private int head;
    private int tail;
    private int size;

    public Fifo(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity < 1");
        }
        @SuppressWarnings("unchecked")
        var array = (E[]) new Object[capacity];
        this.array = array;
    }

    public void offer(E element) {
        Objects.requireNonNull(element);
        if (size == array.length) {
            throw new IllegalStateException("No place");
        }
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
    }

    public E poll() {
        if (size == 0) {
            throw new IllegalStateException("Any element found");
        }
        var element = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        size--;
        return element;
    }

    @Override
    public String toString() {
        var stringJoiner = new StringJoiner(", ", "[", "]");
        var h = head;

        for (var i = 0; i < size; i++) {
            var element = array[h];
            h = (h + 1) % array.length;
            stringJoiner.add(element.toString());
        }
        return stringJoiner.toString();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int h = head;
            private int i = 0;
            @Override
            public boolean hasNext() {
                return i < size;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("it has no next");
                }
                var element = array[h];
                h = (h + 1) % array.length;
                i++;
                return element;
            }
        };
    }
}
