package com.yaodanzhang.kata.texas;

import java.util.Comparator;
import java.util.List;

public interface HandType extends Comparator<List<Poke>> {
    boolean isThisType(List<Poke> pokes);
}
