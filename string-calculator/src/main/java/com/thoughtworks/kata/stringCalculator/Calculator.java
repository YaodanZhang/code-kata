package com.thoughtworks.kata.stringCalculator;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.valueOf;

public class Calculator {

    private static final String DEFAULT_DELIMITER = "(\\,)|(\\n)";

    public int calculate(String numbers) {
        if (Strings.isNullOrEmpty(numbers)) {
            return 0;
        }
        if (isStandardInput(numbers)) {
            return calculateSums(numbers.split(DEFAULT_DELIMITER));
        }
        String inputNumbers = numbers.substring(getFirstNewLineIndex(numbers) + 1);
        String delimiter = String.format(DEFAULT_DELIMITER + "|(%s)", numbers.substring(0, getFirstNewLineIndex(numbers)));
        return calculateSums(inputNumbers.split(delimiter));
    }

    private int getFirstNewLineIndex(String numbers) {
        return numbers.indexOf('\n');
    }

    private boolean isStandardInput(String numbers) {
        Pattern standardPattern = Pattern.compile("\\-?\\d(.|\n)*");
        Matcher matcher = standardPattern.matcher(numbers);
        return matcher.matches();
    }

    private int calculateSums(String[] inputNumbers) {
        int sum = 0;
        for (String number : inputNumbers) {
            int value = valueOf(number);
            if (value < 0) {
                throw new IllegalArgumentException("cannot calculate negative number");
            }
            sum += value;
        }
        return sum;
    }

}
