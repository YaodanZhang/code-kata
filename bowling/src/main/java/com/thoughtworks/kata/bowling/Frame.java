package com.thoughtworks.kata.bowling;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static com.thoughtworks.kata.bowling.BowlingGame.FULL_SCORE;
import static java.util.Optional.ofNullable;

public class Frame {

    private static final int STRIKE_BONUS_ROLLS = 2;
    private static final int SPARE_BONUS_ROLLS = 1;

    private final List<Integer> rolls = newArrayList();
    private Frame next;
    private Frame previous;

    public Frame roll(int roll) {
        Frame frame = this;
        if (isFinished()) {
            frame = startNextFrame();
        }
        frame.rolls.add(roll);
        return frame;
    }

    public int firstRoll() {
        return scoreOfRolls(1);
    }

    public int score() {
        return bonus() + scoreOfRolls(size()) + nextOf(Frame::score);
    }

    private Frame startNextFrame() {
        next = new Frame();
        next.previous = this;
        return next;
    }

    private boolean isFinished() {
        return index() < 10 && (size() == 2 || isStrike());
    }

    private int bonus() {
        if (isSpare()) {
            return nextOf(it -> it.scoreOfRolls(SPARE_BONUS_ROLLS));
        }
        if (isStrike()) {
            return nextOf(it -> it.scoreOfRolls(STRIKE_BONUS_ROLLS));
        }
        return 0;
    }

    private boolean isStrike() {
        return size() == 1 && scoreOfRolls(size()) == FULL_SCORE;
    }

    private boolean isSpare() {
        return size() == 2 && scoreOfRolls(size()) == FULL_SCORE;
    }

    private int nextOf(Function<Frame, Integer> operation) {
        return ofNullable(next).map(operation).orElse(0);
    }

    private int scoreOfRolls(int count) {
        if (count <= 0) {
            return 0;
        }
        return rolls.stream().limit(count).reduce(0, (a, b) -> a + b)
                + nextOf(it -> it.scoreOfRolls(count - size()));
    }

    private int index() {
        return 1 + ofNullable(previous).map(Frame::index).orElse(0);
    }


    private int size() {
        return rolls.size();
    }
}
