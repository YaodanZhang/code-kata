package com.thoughtworks.kata.parking.strategy;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.thoughtworks.kata.parking.Parkable;

import java.util.List;

import static com.google.common.collect.Iterables.tryFind;

public class FirstEmptyStrategy implements ParkableChoosingStrategy {
    @Override
    public Optional<Parkable> findAvailable(List<Parkable> parkables) {
        return tryFind(parkables, firstNotFull());
    }

    private static Predicate<Parkable> firstNotFull() {
        return new Predicate<Parkable>() {
            @Override
            public boolean apply(Parkable parkingLot) {
                return !parkingLot.isFull();
            }
        };
    }
}
