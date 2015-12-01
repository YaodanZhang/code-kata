package com.thoughtworks.pos;

import com.thoughtworks.pos.Result.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PosMachine {
    private final Map<String, Integer> allGoods;

    public PosMachine(Map<String, Integer> allGoods) {
        this.allGoods = allGoods;
    }

    public Result calculate(List<Item> items) {
        List<Record> resultRecords = new ArrayList<Record>();
        for (Item item : items) {
            validateItem(item);
            int price = allGoods.get(item.getBarcode());
            resultRecords.add(new Record(item, price, price * item.getAmount()));
        }
        return new Result(resultRecords);
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
