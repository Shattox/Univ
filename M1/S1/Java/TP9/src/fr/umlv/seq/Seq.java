package fr.umlv.seq;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Seq<E> implements Iterable<E> {
    private final List<Object> list;
    private final Function<? super Object, ? extends E> mapper;

    @SuppressWarnings("unchecked")
    private Seq(List<? extends E> list) {
        this(List.copyOf(list), o -> (E) o);
    }

    private Seq(List<Object> list, Function<? super Object, ? extends E> mapper) {
        this.list = list;
        this.mapper = mapper;
    }

    public static<T> Seq<T> from(List<? extends T> list) {
        Objects.requireNonNull(list);
        return new Seq<>(list);
    }

    public E get(int index) {
        Objects.checkIndex(index, list.size());
        return mapper.apply(list.get(index));
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.stream()
                .map(mapper)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "<", ">"));
    }

    @SafeVarargs
    public static<T> Seq<T> of(T... elements) {
        return new Seq<>(List.of(elements));
    }

    public void forEach(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        list.forEach(o -> consumer.accept(mapper.apply(o)));
    }

    public<T> Seq<T> map(Function<? super E, ? extends T> fun) {
        Objects.requireNonNull(fun);
        return new Seq<>(list, fun.compose(mapper));
    }

    public Optional<E> findFirst() {
        return list.stream()
                .<E>map(mapper)
                .findFirst();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int i;
            @Override
            public boolean hasNext() {
                return i < list.size();
            }
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("it has no next");
                }
                return mapper.apply(list.get(i++));
            }
        };
    }

    @Override
    public Spliterator<E> spliterator () {
        return new Spliterator<>() {
            private final Spliterator<Object> splitIterator = list.spliterator();

            @Override
            public boolean tryAdvance(Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                return splitIterator.tryAdvance(e -> consumer.accept(mapper.apply(e)));
            }

            @SuppressWarnings("unchecked")
            @Override
            public Spliterator<E> trySplit() {
                return (Spliterator<E>) splitIterator.trySplit();
            }

            @Override
            public long estimateSize() {
                return splitIterator.estimateSize();
            }

            @Override
            public int characteristics() {
                return IMMUTABLE | NONNULL | ORDERED;
            }
        };
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
