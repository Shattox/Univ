package fr.umlv.serie;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeSeries<T> {
    public record Data<E>(long timestamp, E element) {
        public Data {
            Objects.requireNonNull(element);
        }

        @Override
        public String toString() {
            return timestamp + " | " + element;
        }
    }

    public class Index implements Iterable<Data<T>> {
        private final int[] array;

        private Index(int[] array) {
            this.array = array;
        }

        public int size() {
            return array.length;
        }

        @Override
        public String toString() {
            return Arrays.stream(array)
                    .mapToObj(list::get)
                    .map(Data::toString)
                    .collect(Collectors.joining("\n"));
        }

        @Override
        public Iterator<Data<T>> iterator() {
            return new Iterator<Data<T>>() {
                private int i;
                @Override
                public boolean hasNext() {
                    return i < array.length;
                }
                public Data<T> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("it has no next");
                    }
                    return list.get(array[i++]);
                }
            };
        }

        public void forEach(Consumer<? super Data<T>> consumer) {
            Arrays.stream(array).mapToObj(list::get).forEach(consumer);
        }

        public Index or(Index index) {
            Objects.requireNonNull(index);
            if (!equals(index)) {
                throw new IllegalArgumentException("Both index need to be in the same TimeSeries");
            }
            var newArray = IntStream.concat(Arrays.stream(index.array), Arrays.stream(this.array))
                    .distinct()
                    .sorted()
                    .toArray();
            return new Index(newArray);
        }

        public Index and(Index index) {
            Objects.requireNonNull(index);
            if (!equals(index)) {
                throw new IllegalArgumentException("Both index need to be in the same TimeSeries");
            }
            var set = Arrays.stream(index.array)
                    .boxed()
                    .collect(Collectors.toSet());
            var newIndex = Arrays.stream(this.array)
                    .filter(set::contains)
                    .toArray();
            return new Index(newIndex);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof TimeSeries.Index index && index.hashCode() == hashCode();
        }

        @Override
        public int hashCode() {
            return list.hashCode();
        }
    }

    private final ArrayList<Data<T>> list = new ArrayList<>();

    public void add(long timestamp, T element) {
        Objects.requireNonNull(element);

        if (!list.isEmpty() && list.get(list.size() - 1).timestamp > timestamp) {
            throw new IllegalStateException("new timestamp need to be >= to the last timestamp");
        }
        list.add(new Data<>(timestamp, element));
    }

    public int size() {
        return list.size();
    }

    public Data<T> get(int index) {
        return list.get(index);
    }

    public Index index() {
        return new Index(IntStream.range(0, size()).toArray());
    }

    public Index index(Predicate<? super T> predicate) {
        var array = IntStream.range(0, list.size())
                .filter(index -> predicate.test(list.get(index).element)).toArray();
        return new Index(array);
    }
}
