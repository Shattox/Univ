package fr.umlv.seq;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Seq2<E> implements Iterable<E> {
    private final Object[] array;
    private final Function<? super Object, ? extends E> mapper;

    @SuppressWarnings("unchecked")
    private Seq2(Object[] array) {
        this(Arrays.copyOf(array, array.length), o -> (E) o);
    }

    private Seq2(Object[] array, Function<? super Object, ? extends E> mapper) {
        Objects.requireNonNull(array);
        Objects.requireNonNull(mapper);
        this.array = array;
        this.mapper = mapper;
    }

    public static<T> Seq2<T> from(List<? extends T> list) {
        Objects.requireNonNull(list);
        list.forEach(elem -> Objects.requireNonNull(elem));
        return new Seq2<>(list.toArray());
    }

    public E get(int index) {
        Objects.checkIndex(index, array.length);
        return mapper.apply(array[index]);
    }

    public int size() {
        return array.length;
    }

    @Override
    public String toString() {
        return Arrays.stream(array)
                .map(mapper)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "<", ">"));
    }

    @SafeVarargs
    public static<T> Seq2<T> of(T... elements) {
        Objects.requireNonNull(Arrays.stream(elements).findAny());
        return new Seq2<>(Arrays.copyOf(elements, elements.length));
    }

    public void forEach(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        Arrays.stream(array).forEach(o -> consumer.accept(mapper.apply(o)));
    }

    public<T> Seq2<T> map(Function<? super E, ? extends T> fun) {
        Objects.requireNonNull(fun);
        return new Seq2<T>(array, fun.compose(mapper));
    }

    public Optional<E> findFirst() {
        return Arrays.stream(array)
                .<E>map(mapper)
                .findFirst();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int i;
            @Override
            public boolean hasNext() {
                return i < array.length;
            }
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("it has no next");
                }
                return mapper.apply(array[i++]);
            }
        };
    }

    public Spliterator<E> spliterator(int start, int end) {
        return new Spliterator<>() {
            private int i = start;

            @Override
            public boolean tryAdvance(Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                if (i < end) {
                    consumer.accept(get(i++));
                    return true;
                }
                return false;
            }

            @SuppressWarnings("unchecked")
            @Override
            public Spliterator<E> trySplit() {
                var middle = (i + end) >>> 1;
                if (middle == i) {
                    return null;
                }
                var spliterator = spliterator(i, middle);
                i = middle;
                return spliterator;
            }

            @Override
            public long estimateSize() {
                return end - i;
            }

            @Override
            public int characteristics() {
                return SIZED | IMMUTABLE | NONNULL | ORDERED;
            }
        };
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(0, array.length), false);
    }
}
