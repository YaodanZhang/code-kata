package com.yaodanzhang.kata.texas;

import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_2;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_3;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_4;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_5;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_A;
import static java.util.stream.Collectors.toList;

public class Straight implements HandType {
    private List<List<Poke.Value>> allStraights = newArrayList();

    private Straight() {
        allStraights.add(newArrayList(POKE_2, POKE_3, POKE_4, POKE_5, POKE_A));
        Poke.Value[] allValues = Poke.Value.values();
        for (int i = 0; i < allValues.length - 4; i++) {
            allStraights.add(newArrayList(
                    allValues[i],
                    allValues[i + 1],
                    allValues[i + 2],
                    allValues[i + 3],
                    allValues[i + 4]
            ));
        }
    }

    @Override
    public boolean isThisType(List<Poke> pokes) {
        return allStraights.contains(getSortedValue(pokes));
    }

    private List<Poke.Value> getSortedValue(List<Poke> pokes) {
        return pokes.stream().map(Poke::getValue).sorted().collect(toList());
    }

    @Override
    public int compare(List<Poke> pokes, List<Poke> otherPokes) {
        return allStraights.indexOf(getSortedValue(pokes)) - allStraights.indexOf(getSortedValue(otherPokes));
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

    public static Straight straight() {
        return new Straight();
    }
}
