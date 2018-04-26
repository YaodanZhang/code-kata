package com.yaodanzhang.kata.texas;

import java.util.List;
import java.util.Objects;

import static com.yaodanzhang.kata.texas.Flush.flush;
import static com.yaodanzhang.kata.texas.Straight.straight;

public class StraightFlush implements HandType {

    @Override
    public boolean isThisType(List<Poke> pokes) {
        return straight().isThisType(pokes) && flush().isThisType(pokes);
    }

    @Override
    public int compare(List<Poke> pokes, List<Poke> otherPokes) {
        return straight().compare(pokes, otherPokes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    static StraightFlush straightFlush() {
        return new StraightFlush();
    }
}
