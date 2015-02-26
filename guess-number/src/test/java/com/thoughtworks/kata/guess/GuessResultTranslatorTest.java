package com.thoughtworks.kata.guess;

import com.thoughtworks.kata.guess.GuessResult;
import com.thoughtworks.kata.guess.GuessResultTranslator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GuessResultTranslatorTest {

    private GuessResultTranslator translator;

    @Before
    public void setUp() {
        translator = new GuessResultTranslator();
    }

    @Test
    public void should_translate_1_perfect_to_1A0B() throws Exception {
        String translateResult = translator.translate(new GuessResult().withPerfectMatch(1));
        assertThat(translateResult, is("1A0B"));
    }

    @Test
    public void should_translate_2_value_match_to_0A2B() throws Exception {
        String translateResult = translator.translate(new GuessResult().withOnlyValueMatch(2));
        assertThat(translateResult, is("0A2B"));
    }

    @Test
    public void should_translate_1_perfect_match_2_value_match_to_1A2B() throws Exception {
        String translateResult = translator.translate(new GuessResult().withPerfectMatch(1).withOnlyValueMatch(2));
        assertThat(translateResult, is("1A2B"));
    }
}
