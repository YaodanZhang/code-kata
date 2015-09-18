package com.yaodanzhang.kata.java8;

public interface Feed<T> {
    T next();

    boolean hasNext();
}
