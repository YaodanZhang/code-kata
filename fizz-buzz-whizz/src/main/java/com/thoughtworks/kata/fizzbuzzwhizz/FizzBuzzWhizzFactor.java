package com.thoughtworks.kata.fizzbuzzwhizz;

public enum FizzBuzzWhizzFactor {
    FIZZ("Fizz"), BUZZ("Buzz"), WHIZZ("Whizz");

    private int key;
    private String description;

    FizzBuzzWhizzFactor(String description) {
        this.description = description;
        key = 1;
    }

    public FizzBuzzWhizzFactor withKey(int key) {
        this.key = key;
        return this;
    }

    public int getKey() {
        return key;
    }

    public boolean isFactor(int digit) {
        return isMod(digit) || isContained(digit);
    }

    private boolean isContained(int digit) {
        return String.valueOf(digit).contains(String.valueOf(key));
    }

    private boolean isMod(int digit) {
        return digit % key == 0;
    }

    @Override
    public String toString() {
        return description;
    }
}
