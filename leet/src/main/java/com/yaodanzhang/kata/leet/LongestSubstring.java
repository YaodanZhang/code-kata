package com.yaodanzhang.kata.leet;

public class LongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int[] indexes = new int[Character.MAX_VALUE];
        int maxLength = 1;

        indexes[s.charAt(0)] = 1;
        for (int i = 0, j = 1, length = s.length(); j < length; j++) {
            int duplicatedIndexAtTheBeginning = indexes[s.charAt(j)];
            if (duplicatedIndexAtTheBeginning != 0) {
                for (; i < duplicatedIndexAtTheBeginning; i++) {
                    indexes[s.charAt(i)] = 0;
                }
            } else {
                maxLength = Math.max(maxLength, j - i + 1);
            }
            indexes[s.charAt(j)] = j + 1;
        }
        return maxLength;
    }
}
