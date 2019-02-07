package com.yaodanzhang.kata.texas;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FirstBiggestComparator implements Comparator<List<Poke>> {
    @Override
    public int compare(List<Poke> pokes, List<Poke> otherPokes) {
        List<Poke.Value> thisSortedValue = sort(pokes);
        List<Poke.Value> otherSortedValue = sort(otherPokes);
        for (int i = thisSortedValue.size() - 1; i >= 0; i--) {
            int compareResult = thisSortedValue.get(i).compareTo(otherSortedValue.get(i));
            if (compareResult == 0) {
                continue;
            }
            return compareResult;
        }
        return 0;
    }

    private List<Poke.Value> sort(List<Poke> pokes) {
        return pokes.stream().map(Poke::getValue).sorted().collect(Collectors.toList());
    }
}
