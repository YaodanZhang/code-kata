package com.thoughtworks.kata.prime;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PrimeFactors {
    public List<Integer> generate(int number) {
        List<Integer> factors = newArrayList();
        for (int i = 2; i <= number;) {
            if (number % i == 0) {
                factors.add(i);
                number /= i;
                continue;
            }
            i++;
        }
        return factors;
    }
}
