package com.yaodanzhang.kata.texas;

public class Poke {
    private final String type;
    private final Value value;

    public Poke(String type, Value value) {
        this.type = type;
        this.value = value;
    }

    static Poke flower(Value value) {
        return new Poke("flower", value);
    }

    static Poke square(Value value) {
        return new Poke("square", value);
    }

    static Poke red(Value value) {
        return new Poke("redHeart", value);
    }

    static Poke black(Value value) {
        return new Poke("black", value);
    }

    public Value getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public enum Value {
        POKE_2,
        POKE_3,
        POKE_4,
        POKE_5,
        POKE_6,
        POKE_7,
        POKE_8,
        POKE_9,
        POKE_10,
        POKE_J,
        POKE_Q,
        POKE_K,
        POKE_A

    }
}
