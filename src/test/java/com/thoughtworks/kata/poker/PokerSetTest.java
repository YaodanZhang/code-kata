package com.thoughtworks.kata.poker;

import org.junit.Ignore;
import org.junit.Test;

import static com.thoughtworks.kata.poker.Card.CardPoint.POINT_2;
import static com.thoughtworks.kata.poker.Card.CardPoint.POINT_3;
import static com.thoughtworks.kata.poker.Card.CardPoint.POINT_4;
import static com.thoughtworks.kata.poker.Card.CardPoint.POINT_5;
import static com.thoughtworks.kata.poker.Card.CardPoint.POINT_6;
import static com.thoughtworks.kata.poker.Card.CardPoint.POINT_7;
import static com.thoughtworks.kata.poker.Card.CardType.HART;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PokerSetTest {

    @Ignore
    @Test
    public void should_return_fist_win_for_high_card() throws Exception {
        PokerSet set = new PokerSet(
                new Card(HART, POINT_2),
                new Card(HART, POINT_3),
                new Card(HART, POINT_4),
                new Card(HART, POINT_5),
                new Card(HART, POINT_6)
        );
        PokerSet anotherSet = new PokerSet(
                new Card(HART, POINT_2),
                new Card(HART, POINT_3),
                new Card(HART, POINT_7),
                new Card(HART, POINT_5),
                new Card(HART, POINT_6)
        );

        assertThat(anotherSet.isBiggerThan(set), is(true));
    }
}
