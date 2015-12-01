package com.thoughtworks.pos;

import java.util.List;
import java.util.Map;

public final class PosMachine {
    private final Map<String, Integer> allGoods;

    public PosMachine(Map<String, Integer> allGoods) {
        this.allGoods = allGoods;
    }

    public int calculate(List<Item> items) {
        int sum = 0;
        for (Item item : items) {
            validateItem(item);
            sum += allGoods.get(item.getBarcode()) * item.getAmount();
        }
        return sum;
    }

    private void validateItem(Item item) {
        if (!allGoods.containsKey(item.getBarcode())) {
            throw new IllegalArgumentException("invalid barcode");
        }

        if (item.getAmount() <= 0) {
            throw new IllegalArgumentException("invalid amount");
        }
    }
}
