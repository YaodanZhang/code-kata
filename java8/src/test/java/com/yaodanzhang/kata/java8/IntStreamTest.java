package com.yaodanzhang.kata.java8;

import org.junit.Before;
import org.junit.Test;

import static com.yaodanzhang.kata.java8.IntStream.range;
import static com.yaodanzhang.kata.java8.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IntStreamTest {

    private IntStream stream;
    private CallControl callControl;

    @Before
    public void setUp() throws Exception {
        stream = range(1, 100);
        callControl = mock(CallControl.class);
    }

    @Test
    public void testFirst() throws Exception {
        assertThat(stream.first(), is(of(1)));
    }

    @Test
    public void testReduce() throws Exception {
        assertThat(stream.reduce((x, y) -> x + y, 0), is(5050));
    }

    @Test
    public void testMap() throws Exception {
        assertThat(stream.map(x -> x * 3).first(), is(of(3)));
    }

    @Test
    public void testLimit() throws Exception {
        assertThat(stream.limit(3).consume().size(), is(3));
    }

    @Test
    public void testFilter() throws Exception {
        assertThat(stream.filter(x -> x % 3 == 0).first(), is(of(3)));
    }

    @Test
    public void testLazyMap() throws Exception {
        stream.map(x -> {
            callControl.call();
            return x * 3;
        });
        verify(callControl, times(0)).call();
    }

    @Test
    public void testLazyLimit() throws Exception {
        stream.map(x -> {
            callControl.call();
            return x;
        }).limit(3);

        verify(callControl, times(0)).call();
    }

    @Test
    public void testLazyFilter() throws Exception {
        stream.filter(x -> {
            callControl.call();
            return x % 3 == 0;
        });

        verify(callControl, times(0)).call();
    }

    @Test
    public void should_(){
        assertThat(stream.filter(x -> x % 2 == 0)
                .filter(x -> x % 3 == 0)
                .first(), is(of(6)));
    }
}
