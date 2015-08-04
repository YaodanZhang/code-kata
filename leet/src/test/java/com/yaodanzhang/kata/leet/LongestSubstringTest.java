package com.yaodanzhang.kata.leet;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LongestSubstringTest {

    private LongestSubstring substring;

    @Before
    public void setUp() {
        substring = new LongestSubstring();
    }

    @Test
    public void should_return_0_for_null() throws Exception {
        int length = substring.lengthOfLongestSubstring(null);
        assertThat(length, is(0));
    }

    @Test
    public void should_return_0_for_empty() throws Exception {
        int length = substring.lengthOfLongestSubstring("");
        assertThat(length, is(0));
    }

    @Test
    public void should_return_1_for_1_letter(){
        int length = substring.lengthOfLongestSubstring("c");
        assertThat(length, is(1));
    }

    @Test
    public void should_return_init_length_for_all_different_string() throws Exception {
        int length = substring.lengthOfLongestSubstring("1234567890");
        assertThat(length, is(10));
    }

    @Test
    public void should_get_length_for_abcab() throws Exception {
        int length = substring.lengthOfLongestSubstring("abcab");
        assertThat(length, is(3));
    }

    @Test
    public void should_get_length_for_abcade() throws Exception {
        int length = substring.lengthOfLongestSubstring("abcade");
        assertThat(length, is(5));
    }
}
