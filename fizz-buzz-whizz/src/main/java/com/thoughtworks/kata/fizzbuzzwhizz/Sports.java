package com.thoughtworks.kata.fizzbuzzwhizz;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.thoughtworks.kata.fizzbuzzwhizz.FizzBuzzWhizzFactor.BUZZ;
import static com.thoughtworks.kata.fizzbuzzwhizz.FizzBuzzWhizzFactor.FIZZ;
import static com.thoughtworks.kata.fizzbuzzwhizz.FizzBuzzWhizzFactor.WHIZZ;

public class Sports {
    private final int studentsAmount;
    private FizzBuzzWhizzTranslator fizzBuzzWhizzTranslator;

    public Sports(int studentsAmount) {
        this.studentsAmount = studentsAmount;
    }

    public List<String> fizzBuzzWhizz(int fizzKey, int buzzKey, int whizzKey) {
        List<FizzBuzzWhizzFactor> fizzBuzzWhizzFactors = createFactors(fizzKey, buzzKey, whizzKey);

        List<String> results = newArrayList();
        for (int i = 1; i <= studentsAmount; i++) {
            results.add(fizzBuzzWhizzTranslator.translate(i, fizzBuzzWhizzFactors));
        }
        return results;
    }

    private List<FizzBuzzWhizzFactor> createFactors(int fizzKey, int buzzKey, int whizzKey) {
        return of(
                    FIZZ.withKey(fizzKey),
                    BUZZ.withKey(buzzKey),
                    WHIZZ.withKey(whizzKey)
            );
    }

    public void setFizzBuzzWhizzTranslator(FizzBuzzWhizzTranslator fizzBuzzWhizzTranslator) {
        this.fizzBuzzWhizzTranslator = fizzBuzzWhizzTranslator;
    }
}
