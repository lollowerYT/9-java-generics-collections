package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;

public final class Pair<T, U> {
    private final T first;
    private final U second;

    // приватный конструктор
    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    // статический фабричный метод
    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<>(first, second);
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    // аналог Optional.ifPresent, но для двух элементов
    public void ifPresent(BiConsumer<? super T, ? super U> consumer) {
        if (consumer != null) {
            consumer.accept(first, second);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return Objects.equals(first, pair.first) &&
               Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair[" + first + ", " + second + "]";
    }
}
