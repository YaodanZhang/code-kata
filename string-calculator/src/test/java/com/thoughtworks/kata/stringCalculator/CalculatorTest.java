package com.thoughtworks.kata.stringCalculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void should_return_0_give_0_number() throws Exception {
        assertThat(calculator.calculate(""), is(0));
    }

    @Test
    public void should_return_1_give_1() throws Exception {
        assertThat(calculator.calculate("1"), is(1));
    }

    @Test
    public void should_return_3_give_1_2() throws Exception {
        assertThat(calculator.calculate("1,2"), is(3));
    }

    @Test
    public void should_return_6_give_1_2_3() throws Exception {
        assertThat(calculator.calculate("1,2,3"), is(6));
    }

    @Test
    public void should_return_6_give_1_2_3_and_new_line() throws Exception {
        assertThat(calculator.calculate("1,2\n3"), is(6));
    }

    @Test
    public void should_return_10_give_1_2_3_4_and_customized_delimiter() throws Exception {
        assertThat(calculator.calculate(";\n1,2\n3;4"), is(10));
    }

    @Test
    public void should_return_15_give_1_2_3_4_5_and_customized_delimiter() throws Exception {
        assertThat(calculator.calculate("-\n1,2\n3-4-5"), is(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_negative() throws Exception {
        calculator.calculate("1,-2,3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_first_negative() throws Exception {
        calculator.calculate("-1,2,3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_customized_negative() throws Exception {
        calculator.calculate("-\n1--2,3");
    }
}
