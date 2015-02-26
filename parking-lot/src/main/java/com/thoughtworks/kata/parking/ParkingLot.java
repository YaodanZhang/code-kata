package com.thoughtworks.kata.parking;

import com.google.common.base.Function;

import java.util.Map;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Maps.newLinkedHashMap;

public class ParkingLot implements Parkable {
    private final int size;
    private Map<Ticket, Car> parkingMap;
    private String name;

    public ParkingLot(int size) {
        this.size = size;
        parkingMap = newLinkedHashMap();
        name = "";
    }

    @Override
    public Ticket park(Car car) {
        if (parkingMap.size() < size) {
            Ticket ticket = new Ticket();
            parkingMap.put(ticket, car);
            return ticket;
        }
        throw new IllegalStateException("parking lot is full");
    }

    @Override
    public Car getCar(Ticket ticket) {
        if (isValid(ticket)) {
            return parkingMap.remove(ticket);
        }
        throw new IllegalArgumentException("ticket is invalid");
    }

    @Override
    public boolean isValid(Ticket ticket) {
        return parkingMap.containsKey(ticket);
    }

    @Override
    public boolean isFull() {
        return parkingMap.size() >= size;
    }

    @Override
    public int getEmptySpace() {
        return size - parkingMap.size();
    }

    @Override
    public double getEmptyRate() {
        return (size - parkingMap.size()) / (double) size;
    }

    public String status() {
        return String.format(
                "{\"Parking Lot\": \"%s\", Cars: [%s]}",
                name, on(", ").join(transform(parkingMap.entrySet(), toCarName()))
        );
    }

    private static Function<Map.Entry<Ticket, Car>, String> toCarName() {
        return new Function<Map.Entry<Ticket, Car>, String>() {
            @Override
            public String apply(Map.Entry<Ticket, Car> entry) {
                return "\"" + entry.getValue().getName() + "\"";
            }
        };
    }

    public void setName(String name) {
        this.name = name;
    }
}
