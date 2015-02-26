package com.thoughtworks.kata.parking;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.thoughtworks.kata.parking.strategy.ParkableChoosingStrategy;

import java.util.List;

import static com.google.common.collect.Iterables.tryFind;
import static com.google.common.collect.Lists.newArrayList;

abstract class ParkableManagement<T extends Parkable> implements Parkable {
    private final ParkableChoosingStrategy parkableChoosingStrategy;
    private List<Parkable> parkables;

    public ParkableManagement(ParkableChoosingStrategy parkableChoosingStrategy) {
        this.parkableChoosingStrategy = parkableChoosingStrategy;
        this.parkables = newArrayList();
    }

    public void addParkables(List<T> parkables) {
        this.parkables.addAll(parkables);
    }

    @Override
    public Ticket park(Car car) {
        Optional<Parkable> availableParkingLot = parkableChoosingStrategy.findAvailable(parkables);
        if (availableParkingLot.isPresent()) {
            return availableParkingLot.get().park(car);
        }
        throw new IllegalStateException("no available parking lot");
    }

    @Override
    public Car getCar(Ticket ticket) {
        Parkable parkable = getParkableOfTicket(ticket);
        if (parkable != null) {
            return parkable.getCar(ticket);
        }
        throw new IllegalArgumentException("ticket is invalid");
    }

    @Override
    public boolean isValid(Ticket ticket) {
        return getParkableOfTicket(ticket) != null;
    }

    private Parkable getParkableOfTicket(Ticket ticket) {
        Parkable parkable = null;
        for (Parkable parkable1 : parkables) {
            if (parkable1.isValid(ticket)) {
                parkable = parkable1;
            }
        }
        return parkable;
    }

    @Override
    public boolean isFull() {
        return !tryFind(parkables, notFull()).isPresent();
    }

    private static Predicate<Parkable> notFull() {
        return new Predicate<Parkable>() {
            @Override
            public boolean apply(Parkable parkable) {
                return !parkable.isFull();
            }
        };
    }

    @Override
    public int getEmptySpace() {
        int sum = 0;
        for (Parkable parkable : parkables) {
            sum += parkable.getEmptySpace();
        }
        return sum;
    }

    @Override
    public double getEmptyRate() {
        double totalSpace = 0.0;
        for (Parkable parkable : parkables) {
            totalSpace += parkable.getEmptySpace() / parkable.getEmptyRate();
        }
        if (totalSpace == 0) {
            return 0;
        }
        return getEmptySpace() / totalSpace;
    }

}
