package fr.umlv.queue;

import java.util.*;

public class ResizeableFifo<E> implements Iterable<E> {
    private E[] array;
    private int head;
    private int tail;
    private int size;

    public ResizeableFifo(int capacity) {
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
            resize();
        }
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
    }

    public E poll() {
        if (size == 0) {
            return null;
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

    private void resize() {
        @SuppressWarnings("unchecked")
        var newArray = (E[]) new Object[array.length * 2];
        System.arraycopy(array, head, newArray, 0, size - head);
        System.arraycopy(array, 0, newArray, size - head, tail);
        head = 0;
        tail = size;
        array = newArray;
    }
}