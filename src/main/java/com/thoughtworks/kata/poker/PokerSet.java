package com.thoughtworks.kata.poker;

import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;

public class PokerSet {
    private final List<Card> cards;

    public PokerSet(List<Card> cards) {
        this.cards = copyOf(cards);
    }

    public PokerSet(Card card, Card card1, Card card2, Card card3, Card card4) {
        this(Lists.<Card>newArrayList());
    }

    public boolean isBiggerThan(PokerSet that) {
        return false;
    }
}
