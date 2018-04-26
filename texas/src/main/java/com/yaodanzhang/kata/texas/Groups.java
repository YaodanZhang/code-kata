package com.yaodanzhang.kata.texas;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.stream.Collectors.toList;

public class Groups implements HandType {

    private final List<Integer> definedGroupSizes;
    private final FirstBiggestComparator firstBiggestComparator;

    private Groups(List<Integer> definedGroupSizes) {
        this.definedGroupSizes = definedGroupSizes.stream().sorted((o1, o2) -> o2 - o1).collect(toList());
        firstBiggestComparator = new FirstBiggestComparator();
    }

    @Override
    public boolean isThisType(List<Poke> pokes) {
        return getGroups(pokes)
                .values()
                .stream()
                .map(List::size)
                .sorted((o1, o2) -> o2 - o1)
                .collect(toList())
                .equals(definedGroupSizes);
    }

    private Map<Poke.Value, List<Poke>> getGroups(List<Poke> pokes) {
        Map<Poke.Value, List<Poke>> pokesGroup = newHashMap();
        pokes.forEach(it -> {
            if (pokesGroup.containsKey(it.getValue())) {
                pokesGroup.get(it.getValue()).add(it);
            } else {
                pokesGroup.put(it.getValue(), newArrayList(it));
            }
        });
        return pokesGroup;
    }

    @Override
    public int compare(List<Poke> pokes, List<Poke> otherPokes) {
        return definedGroupSizes.stream()
                .map(it -> firstBiggestComparator.compare(
                        pokesOf(pokes, sizeOf(it)),
                        pokesOf(otherPokes, sizeOf(it))
                ))
                .filter(it -> it != 0)
                .findFirst()
                .orElse(0);
    }

    private List<Poke> pokesOf(List<Poke> pokes, Predicate<Map.Entry<Poke.Value, List<Poke>>> predicate) {
        return getGroups(pokes).entrySet().stream().filter(predicate).map(it -> it.getValue().get(0)).collect(toList());
    }

    private static Groups groups(Integer... groupSizes) {
        return new Groups(ImmutableList.copyOf(groupSizes));
    }

    static Groups highCard() {
        return groups(1, 1, 1, 1, 1);
    }

    static Groups onePair() {
        return groups(2, 1, 1, 1);
    }

    static Groups twoPairs() {
        return groups(2, 2, 1);
    }

    static Groups threeOfAKind() {
        return groups(3, 1, 1);
    }

    static Groups fullHouse() {
        return groups(3, 2);
    }

    static Groups fourOfAKind() {
        return groups(4, 1);
    }

    private static Predicate<Map.Entry<Poke.Value, List<Poke>>> sizeOf(int groupSize) {
        return it -> it.getValue().size() == groupSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(definedGroupSizes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Groups other = (Groups) obj;
        return Objects.equals(this.definedGroupSizes, other.definedGroupSizes);
    }
}
