package com.thoughtworks.kata.bowling;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BowlingGameTest {
    private final String rolls;
    private final int score;

    @Parameterized.Parameters(name = "score of \"{0}\" is {1}")
    public static Iterable<Object[]> data() {
        return asList(new Object[][]{
                {"12", 3},
                {"1234", 10},

                {"1--2-3-4-", 10},

                {"1/", 10},
                {"1/12", 14},

                {"12X", 13},
                {"12X12", 19},
                {"12XXX12", 70},

                {"XXXXXXXXX12", 247},
                {"XXXXXXXXX1/2", 263},
                {"XXXXXXXXXXXX", 300},
        });
    }

    public BowlingGameTest(String rolls, int score) {
        this.rolls = rolls;
        this.score = score;
    }

    @Test
    public void should_get_score() throws Exception {
        assertThat(new BowlingGame().score(rolls), is(score));
    }
}
