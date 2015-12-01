package com.thoughtworks.pos;

public final class Item {
    private final String barcode;
    private final int amount;

    public Item(String barcode, int amount) {
        this.barcode = barcode;
        this.amount = amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getAmount() {
        return amount;
    }
}
