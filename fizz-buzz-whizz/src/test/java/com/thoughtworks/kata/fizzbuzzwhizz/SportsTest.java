package com.thoughtworks.kata.fizzbuzzwhizz;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SportsTest {

    private Sports sports;

    @Before
    public void setUp() {
        sports = new Sports(3);
        sports.setFizzBuzzWhizzTranslator(getDummyTranslator());
    }

    private FizzBuzzWhizzTranslator getDummyTranslator() {
        return new FizzBuzzWhizzTranslator() {
            @Override
            public String translate(int digit, List<FizzBuzzWhizzFactor> factors) {
                return String.format(
                        "%d translated with: [%d, %d, %d]",
                        digit,
                        factors.get(0).getKey(),
                        factors.get(1).getKey(),
                        factors.get(2).getKey()
                );
            }
        };
    }

    @Test
    public void should_translate_student_digits() throws Exception {
        List<String> actualResult = sports.fizzBuzzWhizz(3, 5, 7);

        List<String> expectedResult = of(
                "1 translated with: [3, 5, 7]",
                "2 translated with: [3, 5, 7]",
                "3 translated with: [3, 5, 7]"
        );
        assertThat(actualResult, is(expectedResult));
    }
}
