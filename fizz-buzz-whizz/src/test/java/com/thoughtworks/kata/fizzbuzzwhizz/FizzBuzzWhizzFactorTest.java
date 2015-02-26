package com.thoughtworks.kata.fizzbuzzwhizz;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzWhizzFactorTest {

    private FizzBuzzWhizzFactor factor;

    @Before
    public void setUp() {
        factor = FizzBuzzWhizzFactor.FIZZ.withKey(3);
    }

    @Test
    public void should_return_true_for_mod() throws Exception {
        assertThat(factor.isFactor(6), is(true));
    }

    @Test
    public void should_return_false_for_not_mod_and_contain() throws Exception {
        assertThat(factor.isFactor(4), is(false));
    }

    @Test
    public void should_return_true_for_contain() throws Exception {
        assertThat(factor.isFactor(31), is(true));
    }

    @Test
    public void should_return_true_for_both_mod_and_contain() throws Exception {
        assertThat(factor.isFactor(36), is(true));
    }
}
