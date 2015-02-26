package com.thoughtworks.kata.fizzbuzzwhizz;

import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;

public class App {
    private static final Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();
    private FizzBuzzWhizzValidator validator = new FizzBuzzWhizzValidator();

    public static void main(String[] args) throws IOException {
        App app = new App();
        app.start();
    }

    private void start() throws IOException {
        List<String> inputs = readInput();
        validator.validate(inputs);
        Sports sports = new Sports(getStudentsAmount(inputs));
        sports.setFizzBuzzWhizzTranslator(new FizzBuzzWhizzTranslator());
        sports.fizzBuzzWhizz(getFizz(inputs), getBuzz(inputs), getWhizz(inputs))
                .forEach(System.out::println);
    }

    private List<String> readInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        List<String> lineList = new ArrayList<>();
        String line = reader.readLine();

        while (!(line == null || line.equals(""))) {
            lineList.add(line.trim().toUpperCase());
            line = reader.readLine();
        }

        reader.close();
        return lineList;
    }

    private int getWhizz(List<String> inputs) {
        return Integer.valueOf(splitFizzBuzzWhizz(inputs).get(2));
    }

    private int getBuzz(List<String> inputs) {
        return Integer.valueOf(splitFizzBuzzWhizz(inputs).get(1));
    }

    private int getFizz(List<String> inputs) {
        return Integer.valueOf(splitFizzBuzzWhizz(inputs).get(0));
    }

    private List<String> splitFizzBuzzWhizz(List<String> inputs) {
        return copyOf(SPLITTER.split(inputs.get(1)));
    }

    private Integer getStudentsAmount(List<String> inputs) {
        return Integer.valueOf(inputs.get(0));
    }
}
