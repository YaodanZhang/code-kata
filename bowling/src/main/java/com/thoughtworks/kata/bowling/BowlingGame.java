package com.thoughtworks.kata.bowling;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.google.common.collect.Maps.newHashMap;
import static com.thoughtworks.kata.bowling.Frame.FULL_SCORE;

@SuppressWarnings("WeakerAccess")
public class BowlingGame {
    private static final Map<Character, Function<Integer, Integer>> SCORES = newHashMap();

    static {
        IntStream.rangeClosed(1, 9).forEach(it -> SCORES.put(Character.forDigit(it, 10), previousScore -> it));
        SCORES.put('-', previousScore -> 0);
        SCORES.put('/', previousScore -> FULL_SCORE - previousScore);
        SCORES.put('X', previousScore -> FULL_SCORE);
    }

    public int score(String rolls) {
        Frame frame = new Frame();
        Frame headFrame = frame;

        for (char roll : rolls.toCharArray()) {
            frame = frame.roll(SCORES.get(roll).apply(frame.firstRoll()));
        }

        return headFrame.score();
    }
}
