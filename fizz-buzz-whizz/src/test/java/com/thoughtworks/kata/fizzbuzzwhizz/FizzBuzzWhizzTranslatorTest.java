package com.thoughtworks.kata.fizzbuzzwhizz;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static com.thoughtworks.kata.fizzbuzzwhizz.FizzBuzzWhizzFactor.BUZZ;
import static com.thoughtworks.kata.fizzbuzzwhizz.FizzBuzzWhizzFactor.FIZZ;
import static com.thoughtworks.kata.fizzbuzzwhizz.FizzBuzzWhizzFactor.WHIZZ;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzWhizzTranslatorTest {

    private FizzBuzzWhizzTranslator fizzBuzzWhizzTranslator;
    private List<FizzBuzzWhizzFactor> factors;

    @Before
    public void setUp() {
        fizzBuzzWhizzTranslator = new FizzBuzzWhizzTranslator();
        factors = of(
                FIZZ.withKey(3),
                BUZZ.withKey(5),
                WHIZZ.withKey(7)
        );
    }

    @Test
    public void should_not_translate_1() throws Exception {
        assertThat(translate(1), is("1"));
    }

    @Test
    public void should_translate_3_to_fizz() throws Exception {
        assertThat(translate(3), is("Fizz"));
    }

    @Test
    public void should_translate_5_to_buzz() throws Exception {
        assertThat(translate(5), is("Buzz"));
    }

    @Test
    public void should_translate_7_to_whizz() throws Exception {
        assertThat(translate(7), is("Whizz"));
    }

    @Test
    public void should_translate_6_to_fizz() throws Exception {
        assertThat(translate(6), is("Fizz"));
    }

    @Test
    public void should_translate_15_to_fizzBuzz() throws Exception {
        assertThat(translate(15), is("FizzBuzz"));
    }

    @Test
    public void should_translate_140_to_BuzzWhizz() throws Exception {
        assertThat(translate(140), is("BuzzWhizz"));
    }

    @Test
    public void should_translate_105_to_FizzBuzzWhizz() throws Exception {
        assertThat(translate(105), is("FizzBuzzWhizz"));
    }

    @Test
    public void should_translate_31_to_fizz() throws Exception {
        assertThat(translate(31), is("Fizz"));
    }

    @Test
    public void should_translate_36_to_Fizz() throws Exception {
        assertThat(translate(36), is("Fizz"));
    }

    @Test
    public void should_translate_30_to_FizzBuzz() throws Exception {
        assertThat(translate(30), is("FizzBuzz"));
    }

    @Test
    public void should_translate_35_to_FizzBuzzWhizz() throws Exception {
        assertThat(translate(35), is("FizzBuzzWhizz"));
    }

    private String translate(int input) {
        return fizzBuzzWhizzTranslator.translate(input, factors);
    }

}
