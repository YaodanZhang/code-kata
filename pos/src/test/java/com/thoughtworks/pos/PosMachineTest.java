package com.thoughtworks.pos;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PosMachineTest {

    private PosMachine machine;

    @Before
    public void setUp() {
        machine = new PosMachine(initAllGoods());
    }

    @Test
    public void should_get_0_given_empty_cart() {
        int result = machine.calculate(Arrays.<Item>asList());
        assertThat(result, is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_invalid_item() {
        machine.calculate(asList(new Item("invalid good", 1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_invalid_amount() {
        machine.calculate(asList(new Item("I1", 0)));
    }

    @Test
    public void should_calculate_given_1_item() {
        int result = machine.calculate(asList(new Item("I1", 1)));
        assertThat(result, is(40));
    }

    @Test
    public void should_calculate_given_1_item_with_multiple_amount() {
        int result = machine.calculate(asList(new Item("I1", 2)));
        assertThat(result, is(80));
    }

    @Test
    public void should_calculate_given_2_items() {
        int result = machine.calculate(asList(new Item("I1", 2), new Item("I2", 1)));
        assertThat(result, is(110));
    }

    private static HashMap<String, Integer> initAllGoods() {
        HashMap<String, Integer> allGoods = new HashMap<String, Integer>();
        allGoods.put("I1", 40);
        allGoods.put("I2", 30);
        return allGoods;
    }
}
