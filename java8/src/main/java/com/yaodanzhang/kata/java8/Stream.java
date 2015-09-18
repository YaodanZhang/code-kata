package com.yaodanzhang.kata.java8;

public class Stream<T> extends BaseStream<T, T> {
    public Stream(Feed<T> feed) {
        super(feed, Function.<T>asItIs());
    }
}
