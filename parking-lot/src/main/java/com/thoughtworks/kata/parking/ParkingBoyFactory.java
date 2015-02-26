package com.thoughtworks.kata.parking;

import com.thoughtworks.kata.parking.strategy.FirstEmptyStrategy;
import com.thoughtworks.kata.parking.strategy.MostEmptyRateStrategy;
import com.thoughtworks.kata.parking.strategy.MostEmptySpaceStrategy;

public class ParkingBoyFactory {
    public static ParkingBoy firstEmptyParkingBoy() {
        return new ParkingBoy(new FirstEmptyStrategy());
    }

    public static ParkingBoy mostEmptySpaceParkingBoy() {
        return new ParkingBoy(new MostEmptySpaceStrategy());
    }

    public static ParkingBoy mostEmptyRateParkingBoy() {
        return new ParkingBoy(new MostEmptyRateStrategy());
    }
}
