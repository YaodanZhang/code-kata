package com.thoughtworks.kata.measurement;

import org.junit.Test;

import static com.thoughtworks.kata.measurement.Length.feet;
import static com.thoughtworks.kata.measurement.Length.inch;
import static com.thoughtworks.kata.measurement.Length.mile;
import static com.thoughtworks.kata.measurement.Length.yard;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class LengthTest {
    @Test
    public void should_not_equals_of_1_mile_2_mile() throws Exception {
        assertThat(mile(1), not(equalTo(mile(2))));
    }

    @Test
    public void should_equals_of_1_mile_1_mile() throws Exception {
        assertThat(mile(1), equalTo(mile(1)));
    }

    @Test
    public void should_equals_of_1_yard_1_yard() throws Exception {
        assertThat(yard(1), equalTo(yard(1)));
    }

    @Test
    public void should_not_equals_of_1_yard_2_yards() throws Exception {
        assertThat(yard(1), not(equalTo(yard(2))));
    }

    @Test
    public void should_equals_of_1_mile_1760_yards() throws Exception {
        assertThat(mile(1), equalTo(yard(1760)));
    }

    @Test
    public void should_not_equals_of_1_mile_1761_yards() throws Exception {
        assertThat(mile(1), not(equalTo(yard(1761))));
    }

    @Test
    public void should_not_equals_of_1761_yards_1_mile() throws Exception {
        assertThat(yard(1761), not(equalTo(mile(1))));
    }

    @Test
    public void should_equals_of_1_yards_3_feet() throws Exception {
        assertThat(yard(1), equalTo(feet(3)));
    }

    @Test
    public void should_not_equals_of_1_yards_4_feet() throws Exception {
        assertThat(yard(1), not(equalTo(feet(4))));
    }

    @Test
    public void should_equals_of_1_feet_12_inch() throws Exception {
        assertThat(feet(1), equalTo(inch(12)));
    }

    @Test
    public void should_not_equals_of_1_feet_13_inch() throws Exception {
        assertThat(feet(1), not(equalTo(inch(13))));
    }

    @Test
    public void should_result_2_miles_plus_1_mile_and_1_mile() throws Exception {
        assertThat(mile(1).plus(mile(1)), is(mile(2)));
    }

    @Test
    public void should_result_1761_yards_plus_1_mile_and_1_yard() throws Exception {
        assertThat(mile(1).plus(yard(1)), is(yard(1761)));
    }
}
