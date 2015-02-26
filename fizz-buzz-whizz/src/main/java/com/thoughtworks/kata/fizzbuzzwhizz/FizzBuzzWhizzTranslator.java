package com.thoughtworks.kata.fizzbuzzwhizz;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

public class FizzBuzzWhizzTranslator {
    public String translate(int digit, List<FizzBuzzWhizzFactor> factors) {
        String result = "";
        for (FizzBuzzWhizzFactor factor : factors) {
            if (factor.isFactor(digit)) {
                result += factor.toString();
            }
        }
        return isNullOrEmpty(result) ? String.valueOf(digit) : result;
    }
}
