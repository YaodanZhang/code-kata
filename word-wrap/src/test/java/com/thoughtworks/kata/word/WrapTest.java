package com.thoughtworks.kata.word;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WrapTest {

    private Wrap wrap;

    @Before
    public void setUp() {
        wrap = new Wrap();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_size_less_than_1() throws Exception {
        wrap.wrap("anyway", 0);
    }

    @Test
    public void should_return_empty_if_word_is_null() throws Exception {
        assertThat(wrap.wrap(null, 1), is(""));
    }

    @Test
    public void should_not_wrap_a_new_line() throws Exception {
        assertThat(wrap.wrap("ab", 3), is("ab"));
        assertThat(wrap.wrap("a b f", 7), is("a b f"));
    }

    @Test
    public void should_only_break() throws Exception {
        assertThat(wrap.wrap("abc", 2), is("ab|c"));
        assertThat(wrap.wrap("abcdef", 2), is("ab|cd|ef"));
    }

    @Test
    public void should_only_wrap_on_space() throws Exception {
        assertThat(wrap.wrap("ab cd", 2), is("ab|cd"));
        assertThat(wrap.wrap("ab cd", 3), is("ab|cd"));
        assertThat(wrap.wrap("ab cd", 4), is("ab|cd"));
        assertThat(wrap.wrap("ab cd ef", 4), is("ab|cd|ef"));
    }
}
