package com.thoughtworks.kata.guess;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;

public class GuessResult {
    private int perfectMatch = 0;
    private int onlyValueMatch = 0;

    public GuessResult() {
    }

    private GuessResult(int perfectMatch, int onlyValueMatch) {
        this.perfectMatch = perfectMatch;
        this.onlyValueMatch = onlyValueMatch;
    }

    public GuessResult withPerfectMatch(int perfectMatch) {
        return new GuessResult(perfectMatch, onlyValueMatch);
    }

    public GuessResult withOnlyValueMatch(int valueMatch) {
        return new GuessResult(perfectMatch, valueMatch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuessResult)) return false;

        GuessResult that = (GuessResult) o;

        return equal(perfectMatch, that.perfectMatch)
                && equal(onlyValueMatch, that.onlyValueMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(perfectMatch, onlyValueMatch);
    }

    public int getPerfectMatch() {
        return perfectMatch;
    }

    public int getOnlyValueMatch() {
        return onlyValueMatch;
    }
}
