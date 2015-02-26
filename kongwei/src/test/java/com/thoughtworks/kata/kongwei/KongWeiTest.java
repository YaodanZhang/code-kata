package com.thoughtworks.kata.kongwei;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KongWeiTest {
    private static int[][] INIT_CELLS = new int[][] {
            {1, 1, 0, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 0, 0, 1},
            {0, 1, 1, 1, 1},
            {0, 1, 0, 1, 1}
    };
    private KongWei kongWei;

    @Before
    public void setUp() {
        kongWei = new KongWei(INIT_CELLS);
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_exception_given_null_array() throws Exception {
        new KongWei(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_empty_array() throws Exception {
        kongWei = new KongWei(new int[0][0]);
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_exception_given_null_column() throws Exception {
        new KongWei(new int[1][]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_given_empty_column() throws Exception {
        new KongWei(new int[1][0]);
    }

    @Test
    public void should_become_0_when_alive_less_than_2() throws Exception {
        assertThat(kongWei.nextFrame()[0][1], is(0));
    }

    @Test
    public void should_keep_1_when_alive_is_2() throws Exception {
    }

    @Test
    public void should_keep_1_when_alive_is_2_at_the_edge() throws Exception {
    }

    @Test
    public void should_keep_0_when_alive_is_2() throws Exception {
    }

    @Test
    public void should_keep_0_when_alive_is_2_at_the_edge() throws Exception {
    }

    @Test
    public void should_become_1_when_alive_is_3() throws Exception {

    }

    @Test
    public void should_become_0_when_alive_more_than_3() throws Exception {

    }

    @Test
    public void should_throw_exception_when_cell_is_null() throws Exception {

    }

    @Test
    public void should_throw_exception_when_cell_is_empty() throws Exception {

    }

    @Test
    public void should_throw_exception_when_cells_contain_2() throws Exception {

    }
}
