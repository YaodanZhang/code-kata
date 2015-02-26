package com.thoughtworks.kata.guess;

import com.thoughtworks.kata.guess.RandomGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RandomGeneratorTest {

    private RandomGenerator generator;

    @Before
    public void setUp() {
        generator = new RandomGenerator();
    }

    @Test
    public void should_generate_4_none_duplicated_digits() throws Exception {
        String digits = generator.generate4DigitsWithoutDuplication();
        assertThat(digits.length(), is(4));
    }

}
