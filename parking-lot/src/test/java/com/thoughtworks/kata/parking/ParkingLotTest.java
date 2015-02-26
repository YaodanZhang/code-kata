package com.thoughtworks.kata.parking;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ParkingLotTest {

    private ParkingLot parkingLot;
    private Car car;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(1);
        car = new Car("BMW: Z-4");
    }

    @Test
    public void should_get_a_ticket_when_park_a_car() throws Exception {
        assertThat(parkingLot.park(car), isA(Ticket.class));
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_park_car_when_parking_lot_is_empty() throws Exception {
        ParkingLot parkingLot = new ParkingLot(0);
        parkingLot.park(car);
    }

    @Test
    public void should_get_car_using_right_ticket() throws Exception {
        Ticket ticket = parkingLot.park(car);
        assertThat(parkingLot.getCar(ticket), sameInstance(car));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_car_using_invalid_ticket() throws Exception {
        parkingLot.park(car);
        parkingLot.getCar(new Ticket());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_car_using_expired_ticket() throws Exception {
        Ticket ticket = parkingLot.park(car);
        parkingLot.getCar(ticket);
        parkingLot.getCar(ticket);
    }

    @Test
    public void should_return_true_if_ticket_is_valid() throws Exception {
        Ticket ticket = parkingLot.park(car);
        assertThat(parkingLot.isValid(ticket), is(true));
    }

    @Test
    public void should_return_false_if_ticket_is_invalid() throws Exception {
        assertThat(parkingLot.isValid(new Ticket()), is(false));
    }

    @Test
    public void should_return_true_if_parking_lot_is_full() throws Exception {
        assertThat(new ParkingLot(0).isFull(), is(true));
    }

    @Test
    public void should_return_false_if_parking_lot_is_not_full() throws Exception {
        assertThat(parkingLot.isFull(), is(false));
    }

    @Test
    public void should_print_status() throws Exception {
        parkingLot = new ParkingLot(2);
        parkingLot.park(new Car("BMW: Z-4"));
        parkingLot.park(new Car("BMW: X-6"));
        parkingLot.setName("Lot1");
        assertThat(parkingLot.status(), is("{\"Parking Lot\": \"Lot1\", Cars: [\"BMW: Z-4\", \"BMW: X-6\"]}"));
    }
}
