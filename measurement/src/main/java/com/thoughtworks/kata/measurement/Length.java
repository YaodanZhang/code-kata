package com.thoughtworks.kata.measurement;

public class Length extends Measurement<Length> {
    private Length(final int length, final Unit unit) {
        super(length, unit);
    }

    public static Length mile(final int length) {
        return new Length(length, LengthUnit.MILE);
    }

    public static Length yard(final int length) {
        return new Length(length, LengthUnit.YARD);
    }

    public static Length feet(final int length) {
        return new Length(length, LengthUnit.FEET);
    }

    public static Length inch(final int length) {
        return new Length(length, LengthUnit.INCH);
    }

    @Override
    protected Length newInstance(final int atomValue) {
        return new Length(atomValue, LengthUnit.INCH);
    }

    private enum LengthUnit implements Unit {
        INCH(1),
        FEET(12 * INCH.atom),
        YARD(3 * FEET.atom),
        MILE(1760 * YARD.atom);

        private final int atom;

        LengthUnit(int atom) {
            this.atom = atom;
        }

        @Override
        public int toAtom() {
            return atom;
        }
    }
}
