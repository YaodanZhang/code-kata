package com.thoughtworks.kata.poker;

public class Card {
    private final CardType type;
    private final CardPoint point;

    public Card(CardType type, CardPoint point) {
        this.type = type;
        this.point = point;
    }

    public static enum CardType {
        HART
    }

    public static enum CardPoint {
        POINT_A,
        POINT_2,
        POINT_3,
        POINT_4,
        POINT_5,
        POINT_6,
        POINT_7,
        POINT_8,
        POINT_9,
        POINT_10,
        POINT_J,
        POINT_Q,
        POINT_K
    }
}
