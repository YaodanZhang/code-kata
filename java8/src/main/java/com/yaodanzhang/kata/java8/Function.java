package com.yaodanzhang.kata.java8;

public interface Function<I, O> {
    O apply(I input);

    static <T> Function<T, T> asItIs() {
        return t -> t;
    }

    default <V> Function<I, V> before(Function<O, V> function) {
        return (I t) -> function.apply(apply(t));
    }
}
