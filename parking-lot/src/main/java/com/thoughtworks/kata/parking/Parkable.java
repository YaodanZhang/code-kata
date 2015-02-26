package com.thoughtworks.kata.parking;

public interface Parkable {
    Ticket park(Car car);

    Car getCar(Ticket ticket);

    boolean isValid(Ticket ticket);

    boolean isFull();

    int getEmptySpace();

    double getEmptyRate();
}
