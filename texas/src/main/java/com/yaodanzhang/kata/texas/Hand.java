package com.yaodanzhang.kata.texas;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.yaodanzhang.kata.texas.Flush.flush;
import static com.yaodanzhang.kata.texas.Groups.fourOfAKind;
import static com.yaodanzhang.kata.texas.Groups.fullHouse;
import static com.yaodanzhang.kata.texas.Groups.highCard;
import static com.yaodanzhang.kata.texas.Groups.onePair;
import static com.yaodanzhang.kata.texas.Groups.threeOfAKind;
import static com.yaodanzhang.kata.texas.Groups.twoPairs;
import static com.yaodanzhang.kata.texas.Straight.straight;
import static com.yaodanzhang.kata.texas.StraightFlush.straightFlush;
import static java.util.Arrays.asList;

public class Hand implements Comparable<Hand> {
    private final ImmutableList<Poke> pokes;

    private List<HandType> handTypes = asList(
            straightFlush(),
            fourOfAKind(),
            fullHouse(),
            flush(),
            straight(),
            threeOfAKind(),
            twoPairs(),
            onePair(),
            highCard()
    );

    Hand(List<Poke> pokes) {
        this.pokes = ImmutableList.copyOf(pokes);
    }

    @Override
    public int compareTo(Hand other) {
        if (this.getHandType().equals(other.getHandType())) {
            return getHandType().compare(pokes, other.pokes);
        }
        return handTypes.indexOf(other.getHandType()) - handTypes.indexOf(this.getHandType());
    }

    private HandType getHandType() {
        return handTypes.stream().filter(it -> it.isThisType(pokes)).findFirst().get();
    }


}
