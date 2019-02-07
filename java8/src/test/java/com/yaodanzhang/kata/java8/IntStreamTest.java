package com.yaodanzhang.kata.java8;

import static com.yaodanzhang.kata.java8.IntStream.range;
import static com.yaodanzhang.kata.java8.Optional.of;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import org.junit.Before;
import org.junit.Test;

public class IntStreamTest {

    private IntStream stream;
    private CallControl callControl;

    @Before
    public void setUp() {
        stream = range(1, 100);
        callControl = mock(CallControl.class);
    }

    @Test
    public void should_sth() {
        asList("1", "2").stream().sorted(Comparator.reverseOrder());

        Collections.sort(asList());
    }

    @Test
    public void testFirst() {
        assertThat(stream.first(), is(of(1)));
    }

    @Test
    public void testReduce() {
        assertThat(stream.reduce((x, y) -> x + y, 0), is(5050));
    }

    @Test
    public void testMap() {
        assertThat(stream.map(x -> x * 3).first(), is(of(3)));
    }

    @Test
    public void testLimit() {
        assertThat(stream.limit(3).consume().size(), is(3));
    }

    @Test
    public void testFilter() {
        assertThat(stream.filter(x -> x % 3 == 0).first(), is(of(3)));
    }

    @Test
    public void testLazyMap() {
        stream.map(x -> {
            callControl.call();
            return x * 3;
        });
        verify(callControl, times(0)).call();
    }

    @Test
    public void testLazyLimit() {
        stream.map(x -> {
            callControl.call();
            return x;
        }).limit(3);

        verify(callControl, times(0)).call();
    }

    @Test
    public void testLazyFilter() {
        stream.filter(x -> {
            callControl.call();
            return x % 3 == 0;
        });

        verify(callControl, times(0)).call();
    }

    @Test
    public void should_filter_twice(){
        assertThat(stream.filter(x -> x % 2 == 0)
                .filter(x -> x % 3 == 0)
                .first(), is(of(6)));
    }

    @Test
    public void should_() throws FileNotFoundException {
        FileInputStream s1 = new FileInputStream
                ("/Users/twer/Documents/development/code/code-kata/build.gradle");
        FileInputStream s2 = new FileInputStream
                ("/Users/twer/Documents/development/code/code-kata/build.gradle");
        System.out.println(s1);
        System.out.println(s2);
    }
}
