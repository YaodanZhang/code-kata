package com.yaodanzhang.kata.texas;

import org.junit.Test;

import static com.yaodanzhang.kata.texas.Poke.Value.POKE_10;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_2;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_3;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_4;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_5;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_6;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_7;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_8;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_9;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_A;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_J;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_K;
import static com.yaodanzhang.kata.texas.Poke.Value.POKE_Q;
import static com.yaodanzhang.kata.texas.Poke.black;
import static com.yaodanzhang.kata.texas.Poke.flower;
import static com.yaodanzhang.kata.texas.Poke.red;
import static com.yaodanzhang.kata.texas.Poke.square;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class HandTest {
    @Test
    public void should_equal_with_all_the_same() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_3), black(POKE_5), black(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_3), black(POKE_5), black(POKE_7), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), is(0));
    }

    @Test
    public void should_compare_biggest_one() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_3), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_3), black(POKE_5), black(POKE_7), black(POKE_10)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_second_largest() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_3), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_3), black(POKE_5), black(POKE_8), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_third_largest() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_3), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_3), black(POKE_6), black(POKE_7), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void one_pair_should_greater_than_high_card() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_3), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_10), black(POKE_5), black(POKE_7), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_one_pair() throws Exception {
        Hand hand = new Hand(asList(black(POKE_2), red(POKE_2), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_5), black(POKE_7), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_equal_one_pair() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_3), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_5), black(POKE_7), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), is(0));
        assertThat(anotherHand.compareTo(hand), is(0));
    }

    @Test
    public void should_compare_first_biggest_of_one_pair() throws Exception {
        Hand hand = new Hand(asList(black(POKE_2), red(POKE_2), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_6), black(POKE_7), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void two_pairs_should_greater_than_one_pair() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_10), square(POKE_5), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_6), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_two_pairs() throws Exception {
        Hand hand = new Hand(asList(black(POKE_2), red(POKE_2), square(POKE_6), flower(POKE_6), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_6), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_first_biggest_ungrouped_two_pairs() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_3), square(POKE_6), flower(POKE_6), black(POKE_8)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_6), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_equal_two_pairs() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_3), square(POKE_6), flower(POKE_6), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_6), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), is(0));
        assertThat(anotherHand.compareTo(hand), is(0));
    }

    @Test
    public void three_of_a_kind_should_greater_than_two_pairs() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_10), square(POKE_7), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_3), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_three_of_a_kind() throws Exception {
        Hand hand = new Hand(asList(black(POKE_2), red(POKE_2), black(POKE_2), black(POKE_6), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_10), square(POKE_10), flower(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_first_biggest_ungrouped_three_of_a_kind() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_3), square(POKE_3), flower(POKE_5), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_3), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_equal_three_of_a_kind() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_3), square(POKE_3), flower(POKE_6), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_3), black(POKE_3), black(POKE_6), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), is(0));
        assertThat(anotherHand.compareTo(hand), is(0));
    }

    @Test
    public void straight_should_greater_than_three_of_a_kind() throws Exception {
        Hand hand = new Hand(asList(black(POKE_10), red(POKE_10), square(POKE_10), flower(POKE_7), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_A), red(POKE_2), square(POKE_3), flower(POKE_4), black(POKE_5)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_straight_with_a_at_last() throws Exception {
        Hand hand = new Hand(asList(black(POKE_9), red(POKE_10), square(POKE_J), flower(POKE_Q), black(POKE_K)));
        Hand anotherHand = new Hand(asList(black(POKE_10), red(POKE_J), black(POKE_Q), black(POKE_K), black(POKE_A)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_straight_with_a_at_first() throws Exception {
        Hand hand = new Hand(asList(black(POKE_A), red(POKE_2), square(POKE_3), flower(POKE_4), black(POKE_5)));
        Hand anotherHand = new Hand(asList(black(POKE_2), red(POKE_3), black(POKE_4), black(POKE_5), black(POKE_6)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_equal_straight() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_4), square(POKE_5), flower(POKE_6), black(POKE_7)));
        Hand anotherHand = new Hand(asList(black(POKE_3), red(POKE_4), black(POKE_5), black(POKE_6), black(POKE_7)));

        assertThat(hand.compareTo(anotherHand), is(0));
        assertThat(anotherHand.compareTo(hand), is(0));
    }

    @Test
    public void flush_should_greater_than_straight() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), red(POKE_4), square(POKE_5), flower(POKE_6), black(POKE_7)));
        Hand anotherHand = new Hand(asList(black(POKE_3), black(POKE_4), black(POKE_8), black(POKE_2), black(POKE_9)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void should_compare_flush() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), black(POKE_10), square(POKE_5), flower(POKE_6), black(POKE_7)));
        Hand anotherHand = new Hand(asList(black(POKE_3), black(POKE_4), black(POKE_8), black(POKE_2), black(POKE_A)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void four_house_should_greater_than_flush() throws Exception {
        Hand hand = new Hand(asList(black(POKE_3), black(POKE_4), black(POKE_8), black(POKE_2), black(POKE_9)));
        Hand anotherHand = new Hand(asList(black(POKE_4), red(POKE_4), square(POKE_4), flower(POKE_7), black(POKE_7)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

    @Test
    public void four_of_a_kind_should_greater_than_full_house() throws Exception {
        Hand hand = new Hand(asList(black(POKE_4), red(POKE_4), square(POKE_4), flower(POKE_7), black(POKE_7)));
        Hand anotherHand = new Hand(asList(black(POKE_4), red(POKE_4), square(POKE_4), flower(POKE_4), black(POKE_7)));

        assertThat(hand.compareTo(anotherHand), lessThan(0));
        assertThat(anotherHand.compareTo(hand), greaterThan(0));
    }

}
