package com.yaodanzhang.kata.java8;

import java.util.ArrayList;
import java.util.List;

import static com.yaodanzhang.kata.java8.Optional.absent;
import static com.yaodanzhang.kata.java8.Optional.of;

public class BaseStream<F, T> {

    private final Feed<F> feed;
    private Function<F, T> function;
    private Predicate<T> predicate;
    private int limit;

    public BaseStream(Feed<F> feed, Function<F, T> function) {
        this.feed = feed;
        this.function = function;
        this.predicate = (t) -> true;
        this.limit = Integer.MAX_VALUE;
    }

    public Optional<T> first() {
        List<T> result = consume(1);
        return result.isEmpty() ? absent() : of(result.get(0));
    }

    public T reduce(BiFunction<T, T, T> function, T start) {
        T result = start;
        for (T t : consume()) {
            result = function.apply(result, t);
        }
        return result;
    }

    public List<T> consume() {
        return consume(limit);
    }

    private List<T> consume(int limit) {
        List<T> results = new ArrayList<>();
        int count = 0;
        while (count < limit && feed.hasNext()) {
            T next = function.apply(feed.next());
            if (predicate.test(next)) {
                results.add(next);
                count++;
            }
        }
        return results;
    }

    public <K> BaseStream<F, K> map(Function<T, K> function) {
        return new BaseStream<>(feed, this.function.before(function));
    }

    public BaseStream<F, T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public BaseStream<F, T> filter(Predicate<T> predicate) {
        this.predicate = this.predicate.and(predicate);
        return this;
    }
}
