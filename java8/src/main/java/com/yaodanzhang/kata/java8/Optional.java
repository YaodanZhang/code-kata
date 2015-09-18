package com.yaodanzhang.kata.java8;

import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.Objects.hash;

public final class Optional<T> {
    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> absent() {
        return new Optional<>(null);
    }

    public static <T> Optional<T> of(T t) {
        return new Optional<>(t);
    }

    public T get() {
        if (value != null) {
            return value;
        }
        throw new NoSuchElementException("Optional is absent");
    }

    public boolean isPresent() {
        return value != null;
    }

    @Override
    public int hashCode() {
        return hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Optional other = (Optional) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return Objects.toString(value);
    }
}
