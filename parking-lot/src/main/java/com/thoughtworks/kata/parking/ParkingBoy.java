package com.thoughtworks.kata.parking;

import com.thoughtworks.kata.parking.strategy.ParkableChoosingStrategy;

public class ParkingBoy extends ParkableManagement<ParkingLot> {
    public ParkingBoy(ParkableChoosingStrategy parkableChoosingStrategy) {
        super(parkableChoosingStrategy);
    }
}
