package com.yaodanzhang.kata.java8;

public interface Predicate<T> {
    boolean test(T input);

    default Predicate<T> and(Predicate<T> another) {
        return (input) -> test(input) && another.test(input);
    }
}
