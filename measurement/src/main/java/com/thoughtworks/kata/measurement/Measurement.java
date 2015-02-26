package com.thoughtworks.kata.measurement;

import static com.google.common.base.Objects.equal;
import static java.util.Objects.hash;

abstract class Measurement<T extends Measurement> {
    private final int value;
    private final Unit unit;

    public Measurement(final int value, final Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement)) return false;

        Measurement that = (Measurement) o;
        return equal(getAtomValue(), that.getAtomValue());
    }

    private int getAtomValue() {
        return value * unit.toAtom();
    }

    @Override
    public int hashCode() {
        return hash(getAtomValue());
    }

    public T plus(final T that) {
        return newInstance(getAtomValue() + ((Measurement) that).getAtomValue());
    }

    protected abstract T newInstance(final int atomValue);
}
