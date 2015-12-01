package com.thoughtworks.pos;

import java.util.List;

public class Result {
    private final List<Record> records;

    public Result(List<Record> records) {
        this.records = records;
    }

    public static class Record {
        private final Item item;
        private final int price;
        private final int total;

        public Record(Item item, int price, int total) {
            this.item = item;
            this.price = price;
            this.total = total;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Record)) return false;

            Record record = (Record) o;

            if (price != record.price) return false;
            if (total != record.total) return false;
            if (item != null ? !item.equals(record.item) : record.item != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = item != null ? item.hashCode() : 0;
            result = 31 * result + price;
            result = 31 * result + total;
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;

        Result result = (Result) o;

        return !(records != null ? !records.equals(result.records) : result.records != null);

    }

    @Override
    public int hashCode() {
        return records != null ? records.hashCode() : 0;
    }
}
