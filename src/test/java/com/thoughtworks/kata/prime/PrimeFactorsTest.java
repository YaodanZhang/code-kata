package com.thoughtworks.kata.prime;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PrimeFactorsTest {

    private PrimeFactors primeFactors;

    @Before
    public void setUp() {
        primeFactors = new PrimeFactors();
    }

    @Test
    public void should_divide_from_1_to_empty() throws Exception {
        List<Integer> expectedResult = of();
        assertThat(primeFactors.generate(1), is(expectedResult));
    }

    @Test
    public void should_divide_from_0_to_empty() throws Exception {
        List<Integer> expectedResult = of();
        assertThat(primeFactors.generate(0), is(expectedResult));
    }

    @Test
    public void should_divide_from_negative_to_empty() throws Exception {
        List<Integer> expectedResult = of();
        assertThat(primeFactors.generate(-1), is(expectedResult));
    }

    @Test
    public void should_divide_from_2_to_2() throws Exception {
        List<Integer> expectedResult = of(2);
        assertThat(primeFactors.generate(2), is(expectedResult));
    }

    @Test
    public void should_divide_from_3_to_3() throws Exception {
        List<Integer> expectedResult = of(3);
        assertThat(primeFactors.generate(3), is(expectedResult));
    }

    @Test
    public void should_divide_from_6_to_2_3() throws Exception {
        List<Integer> expectedResult = of(2, 3);
        assertThat(primeFactors.generate(6), is(expectedResult));
    }

    @Test
    public void should_divide_from_4_to_2_2() throws Exception {
        List<Integer> expectedResult = of(2, 2);
        assertThat(primeFactors.generate(4), is(expectedResult));
    }

    @Test
    public void should_divide_from_12_to_2_2_3() throws Exception {
        List<Integer> expectedResult = of(2, 2, 3);
        assertThat(primeFactors.generate(12), is(expectedResult));
    }

    @Test
    public void should_divide_from_20_to_2_2_5() throws Exception {
        List<Integer> expectedResult = of(2, 2, 5);
        assertThat(primeFactors.generate(20), is(expectedResult));
    }
}
