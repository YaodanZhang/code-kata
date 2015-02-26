package com.thoughtworks.kata.parking.strategy;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.thoughtworks.kata.parking.Parkable;

import java.util.List;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Ordering.natural;

public class MostEmptySpaceStrategy implements ParkableChoosingStrategy {
    @Override
    public Optional<Parkable> findAvailable(final List<Parkable> parkables) {
        List<Parkable> notFullParkables = from(parkables).filter(notFull()).toList();
        if (!notFullParkables.isEmpty()) {
            return of(natural().onResultOf(emptySpace()).max(notFullParkables));
        }
        return absent();
    }

    private static Predicate<Parkable> notFull() {
        return new Predicate<Parkable>() {
            @Override
            public boolean apply(Parkable parkable) {
                return !parkable.isFull();
            }
        };
    }

    private static Function<Parkable, Integer> emptySpace() {
        return new Function<Parkable, Integer>() {
            @Override
            public Integer apply(Parkable parkable) {
                return parkable.getEmptySpace();
            }
        };
    }
}
