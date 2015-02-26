package com.thoughtworks.kata.parking;

import com.thoughtworks.kata.parking.strategy.ParkableChoosingStrategy;

public class ParkingManager extends ParkableManagement<Parkable> {
    public ParkingManager(ParkableChoosingStrategy parkableChoosingStrategy) {
        super(parkableChoosingStrategy);
    }
}
