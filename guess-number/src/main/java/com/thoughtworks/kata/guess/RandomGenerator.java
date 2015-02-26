package com.thoughtworks.kata.guess;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

public class RandomGenerator {
    public String generate4DigitsWithoutDuplication() {
        Set<Integer> digits = Sets.newHashSet();
        while (digits.size() < 4) {
            digits.add(randomDigit());
        }
        return Joiner.on("").join(digits);
    }

    private static int randomDigit() {
        Random random = new Random();
        return random.nextInt(10);
    }
}
