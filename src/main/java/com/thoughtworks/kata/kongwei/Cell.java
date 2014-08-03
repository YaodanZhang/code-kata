package com.thoughtworks.kata.kongwei;

import java.util.List;

public class Cell {
    private final Coordinate coordinate;
    private final int value;

    public Cell(Coordinate coordinate, int value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<Coordinate> getNeighborCoordinates() {
        return null;
    }
}
