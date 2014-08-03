package com.thoughtworks.kata.guess;

import com.google.common.base.Function;
import com.google.common.base.Splitter;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;

public class GuessGame {
    private static final Splitter SPLITTER = Splitter.fixedLength(1);
    private final List<Integer> numbers;

    public GuessGame(String numbers) {
        this.numbers = splitToDigits(numbers);
    }

    public GuessResult guess(String guess) {
        List<Integer> guessDigits = splitToDigits(guess);
        int perfectMatches = getPerfectMatches(guessDigits);
        return new GuessResult()
                .withPerfectMatch(perfectMatches)
                .withOnlyValueMatch(getValueMatches(guessDigits) - perfectMatches);
    }

    private int getValueMatches(List<Integer> guessDigits) {
        int valueMatches = 0;
        for (final int initNumber : numbers) {
            if (guessDigits.contains(initNumber)) {
                valueMatches++;
            }
        }
        return valueMatches;
    }

    private int getPerfectMatches(List<Integer> guessDigits) {
        int perfectMatches = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (guessDigits.get(i).equals(numbers.get(i))) {
                perfectMatches++;
            }
        }
        return perfectMatches;
    }

    private static List<Integer> splitToDigits(String numbers) {
        return from(SPLITTER.split(numbers))
                .transform(toDigit())
                .toList();
    }

    private static Function<String, Integer> toDigit() {
        return new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.valueOf(input);
            }
        };
    }
}
