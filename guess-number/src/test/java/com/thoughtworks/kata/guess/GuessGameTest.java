package com.thoughtworks.kata.guess;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GuessGameTest {

    private GuessGame game;

    @Before
    public void setUp() {
        game = new GuessGame("1234");
    }

    @Test
    public void should_return_none_match() throws Exception {
        assertThat(game.guess("5678"), is(new GuessResult()));
    }

    @Test
    public void should_return_1_perfect_match() throws Exception {
        assertThat(game.guess("1678"), is(new GuessResult().withPerfectMatch(1)));
    }

    @Test
    public void should_return_2_perfect_match() throws Exception {
        assertThat(game.guess("1278"), is(new GuessResult().withPerfectMatch(2)));
    }

    @Test
    public void should_return_2_value_match() throws Exception {
        assertThat(game.guess("5128"), is(new GuessResult().withOnlyValueMatch(2)));
    }

    @Test
    public void should_return_2_perfect_match_and_3_value_match() throws Exception {
        assertThat(game.guess("1273"), is(new GuessResult().withPerfectMatch(2).withOnlyValueMatch(1)));
    }
}
