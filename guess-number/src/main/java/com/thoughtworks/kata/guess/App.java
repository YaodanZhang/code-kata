package com.thoughtworks.kata.guess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.google.common.base.Strings.isNullOrEmpty;

public class App {

    private static final int MAX_ATTEMPT = 6;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        RandomGenerator generator = new RandomGenerator();
        GuessResultTranslator translator = new GuessResultTranslator();
        String numbers = generator.generate4DigitsWithoutDuplication();
        GuessGame guessGame = new GuessGame(numbers);

        System.out.println("game begin");

        for (int i = 0; i < MAX_ATTEMPT; i++) {
            String line = reader.readLine();
            if (isNullOrEmpty(line)) {
                return;
            }
            System.out.println(translator.translate(guessGame.guess(line)));
        }

        System.out.println("digits: " + numbers);
    }
}
