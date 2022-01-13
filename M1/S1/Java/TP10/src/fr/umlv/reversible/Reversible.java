package fr.umlv.reversible;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Reversible<E> extends Iterable<E> {
    int size();
    E get(int index);

    @SafeVarargs
    static<T> Reversible<T> fromArray(T... elements) {
        return fromList(Arrays.asList(elements));
    }

    static<T> Reversible<T> fromList(List<? extends T> list) {
        Objects.requireNonNull(list);
        list.forEach(Objects::requireNonNull);
        var size = list.size();
        return new Reversible<>() {
            @Override
            public int size() {
                return size;
            }

            /**
             * throws ISE if the size of the underlying data structure is
             * less than the size of the current Reversible.
             * @param index index
             * @return value from index
             */
            @Override
            public T get(int index) {
                Objects.checkIndex(index, size);
                if (list.size() < size) {
                    throw new IllegalStateException();
                }
                Objects.requireNonNull(list.get(index));
                return list.get(index);
            }
        };
    }

    default Iterator<E> iterator() {
        return new Iterator<>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                try {
                    return get(index++);
                } catch (IllegalStateException e) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    default Reversible<E> reversed() {
        return new Reversible<>() {
            @Override
            public int size() {
                return Reversible.this.size();
            }

            @Override
            public E get(int index) {
                return Reversible.this.get(size() - 1 - index);
            }

            @Override
            public Reversible<E> reversed() {
                return Reversible.this;
            }
        };
    }

    default Spliterator<E> spliterator(int start, int end) {
        return new Spliterator<>() {
            private int i = start;

            @Override
            public boolean tryAdvance(Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                if (i < end) {
                    try {
                        consumer.accept(get(i++));
                    } catch (IllegalStateException e) {
                        throw new ConcurrentModificationException();
                    }
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
                return SIZED | NONNULL | ORDERED;
            }
        };
    }

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(0, size()), true);
    }
}
