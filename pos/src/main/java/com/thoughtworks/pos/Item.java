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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (amount != item.amount) return false;
        if (barcode != null ? !barcode.equals(item.barcode) : item.barcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = barcode != null ? barcode.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }
}
