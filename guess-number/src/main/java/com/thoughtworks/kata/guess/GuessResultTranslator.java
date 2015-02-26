package com.thoughtworks.kata.guess;

import static java.lang.String.format;

public class GuessResultTranslator {

    private static final String translateTemplate = "%sA%sB";

    public String translate(GuessResult guessResult) {
        return format(
                translateTemplate,
                guessResult.getPerfectMatch(),
                guessResult.getOnlyValueMatch()
        );
    }
}
