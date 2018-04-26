package com.yaodanzhang.kata.texas;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Flush implements HandType {
    private FirstBiggestComparator firstBiggestComparator = new FirstBiggestComparator();
    @Override
    public boolean isThisType(List<Poke> pokes) {
        return pokes.stream()
                .map(Poke::getType)
                .collect(Collectors.toSet())
                .size() == 1;
    }

    @Override
    public int compare(List<Poke> pokes, List<Poke> otherPokes) {
        return firstBiggestComparator.compare(pokes, otherPokes);
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

    public static Flush flush() {
        return new Flush();
    }
}
