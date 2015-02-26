package com.thoughtworks.kata.bowling;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BowlingTest {

    private Bowling bowling;

    @Before
    public void setUp() {
        bowling = new Bowling();
    }

    @Test
    public void should_return_5_of_a_single_kick() throws Exception {
        List<Integer> scores = of(5);
        for (int score : scores) {
            bowling.hit(score);
        }
        assertThat(bowling.getScore(), is(5));
    }

    @Test
    public void should_return_8_of_5_3() throws Exception {
        List<Integer> scores = of(5, 3);
        for (int score : scores) {
            bowling.hit(score);
        }
        assertThat(bowling.getScore(), is(8));
    }

    @Test
    public void should_return_13_of_5_5_3() throws Exception {
        List<Integer> scores = of(5, 5, 3);
        for (int score : scores) {
            bowling.hit(score);
        }
        assertThat(bowling.getScore(), is(16));
    }

    @Test
    public void should_return_18_of_10_5_3() throws Exception {
        List<Integer> scores = of(10, 5, 3);
        for (int score : scores) {
            bowling.hit(score);
        }
        assertThat(bowling.getScore(), is(26));
    }

    @Test
    public void should_return_300_of_all_10() throws Exception {
        List<Integer> scores = of(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        for (int score : scores) {
            bowling.hit(score);
        }
        assertThat(bowling.getScore(), is(300));
    }
}
