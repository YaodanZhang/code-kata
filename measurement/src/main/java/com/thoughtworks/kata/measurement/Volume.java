package com.thoughtworks.kata.measurement;

public class Volume extends Measurement<Volume> {
    private Volume(int volume, Unit unit) {
        super(volume, unit);
    }

    public static Volume oz(int volume) {
        return new Volume(volume, VolumeUnit.OZ);
    }

    public static Volume tbsp(int volume) {
        return new Volume(volume, VolumeUnit.TBSP);
    }

    public static Volume tsp(int volume) {
        return new Volume(volume, VolumeUnit.TSP);
    }

    @Override
    protected Volume newInstance(int atomValue) {
        return new Volume(atomValue, VolumeUnit.TSP);
    }

    private enum VolumeUnit implements Unit {
        TSP(1),
        TBSP(3 * TSP.atom),
        OZ(2 * TBSP.atom);
        private final int atom;

        private VolumeUnit(int atom) {
            this.atom = atom;
        }

        @Override
        public int toAtom() {
            return atom;
        }
    }
}
