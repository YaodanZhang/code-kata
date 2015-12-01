package com.thoughtworks.pos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

public class GoodsParser {

    private static final Pattern PATTERN = compile("^(\\w+):(\\d+)$");

    public Map<String, Integer> parseAllGoods(List<String> input) {
        Map<String, Integer> allGoods = new HashMap<String, Integer>();
        for (String oneGood : input) {
            validateInput(oneGood);
            String[] splitInput = oneGood.split(":");
            allGoods.put(splitInput[0], parseInt(splitInput[1]));
        }
        return allGoods;
    }

    private static void validateInput(String oneGood) {
        if (!PATTERN.matcher(oneGood).matches()) {
            throw new IllegalArgumentException("invalid input format");
        }
    }
}
