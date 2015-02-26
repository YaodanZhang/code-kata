package com.thoughtworks.kata.measurement;

import org.junit.Test;

import static com.thoughtworks.kata.measurement.Volume.oz;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VolumeTest {
    @Test
    public void should_equals_of_1_oz_1_oz() throws Exception {
        assertThat(oz(1), is(oz(1)));
    }

}
