package com.thoughtworks.kata.parking.strategy;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.kata.parking.Parkable;
import com.thoughtworks.kata.parking.ParkingLot;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class FirstEmptyStrategyTest {

    private ParkableChoosingStrategy parkableChoosingStrategy;
    private ParkingLot emptyParkingLot;
    private ParkingLot fullParkingLot;

    @Before
    public void setUp() {
        parkableChoosingStrategy = new FirstEmptyStrategy();
        emptyParkingLot = new ParkingLot(1);
        fullParkingLot = new ParkingLot(0);
    }

    @Test
    public void should_return_first_parking_lot_if_first_not_full() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(emptyParkingLot, new ParkingLot(3)));
        assertThat(parkingLot.get(), sameInstance((Parkable) emptyParkingLot));
    }

    @Test
    public void should_return_second_parking_lot_if_first_is_full_but_second_is_not() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(fullParkingLot, emptyParkingLot));
        assertThat(parkingLot.get(), sameInstance((Parkable) emptyParkingLot));
    }

    @Test
    public void should_return_absent_if_all_parking_lots_are_full() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of(fullParkingLot, new ParkingLot(0)));
        assertThat(parkingLot.isPresent(), is(false));
    }

    @Test
    public void should_return_absent_if_there_is_not_a_parking_lot() throws Exception {
        Optional<Parkable> parkingLot =
                parkableChoosingStrategy.findAvailable(ImmutableList.<Parkable>of());
        assertThat(parkingLot.isPresent(), is(false));
    }
}
