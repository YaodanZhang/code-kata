package com.thoughtworks.kata.parking.strategy;

import com.google.common.base.Optional;
import com.thoughtworks.kata.parking.Parkable;

import java.util.List;

public interface ParkableChoosingStrategy {
    Optional<Parkable> findAvailable(List<Parkable> parkables);
}
