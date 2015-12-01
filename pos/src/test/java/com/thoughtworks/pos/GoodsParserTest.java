package com.thoughtworks.pos;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GoodsParserTest {

    private GoodsParser parser;

    @Before
    public void setUp() {
        parser = new GoodsParser();
    }

    @Test
    public void should_get_empty_goods_given_none() {
        Map<String, Integer> allGoods = parser.parseAllGoods(Arrays.<String>asList());
        Map<String, Integer> expectedGoods = new HashMap<String, Integer>();
        assertThat(allGoods, is(expectedGoods));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_given_invalid_input() {
        parser.parseAllGoods(asList("blabla"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_given_invalid_price() {
        parser.parseAllGoods(asList("blabla:d8"));
    }

    @Test
    public void should_get_1_good() {
        Map<String, Integer> allGoods = parser.parseAllGoods(asList("I1:40"));
        Map<String, Integer> expectedGoods = new HashMap<String, Integer>();
        expectedGoods.put("I1", 40);
        assertThat(allGoods, is(expectedGoods));
    }

    @Test
    public void should_get_2_goods() {
        Map<String, Integer> allGoods = parser.parseAllGoods(asList("I1:40", "I2:30"));
        Map<String, Integer> expectedGoods = new HashMap<String, Integer>();
        expectedGoods.put("I1", 40);
        expectedGoods.put("I2", 30);
        assertThat(allGoods, is(expectedGoods));
    }
}
