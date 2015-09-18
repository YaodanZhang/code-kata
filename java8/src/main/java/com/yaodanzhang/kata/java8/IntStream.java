package com.yaodanzhang.kata.java8;

public class IntStream extends Stream<Integer> {

    private IntStream(int min, int max) {
        super(new Feed<Integer>() {
            private Integer current = min;

            @Override
            public Integer next() {
                return current++;
            }

            @Override
            public boolean hasNext() {
                return current <= max;
            }
        });
    }

    public static IntStream range(int min, int max) {
        return new IntStream(min, max);
    }
}
