package com.yaodanzhang.kata.leet;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] calculate(int[] nums, Integer target) {
        Map<Integer, Integer> input = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            input.put(i, nums[i]);
        }

        for (Map.Entry<Integer, Integer> entry : input.entrySet()) {
            Integer thisKey = entry.getKey();
            Integer anotherKey = target - thisKey;
            Integer thisIndex = entry.getValue();
            Integer anotherIndex = input.get(anotherKey);

            if (input.containsKey(anotherKey) && !anotherIndex.equals(thisIndex)) {
                return new int[]{thisIndex, anotherIndex};
            }
        }

        return null;
    }
}
