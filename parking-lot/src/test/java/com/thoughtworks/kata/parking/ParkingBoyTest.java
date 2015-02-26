package com.thoughtworks.kata.parking;

import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.kata.parking.strategy.FirstEmptyStrategy;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParkingBoyTest {

    private ParkingBoy parkingBoy;
    private ParkingLot emptyParkingLot;
    private Car car;
    private ParkingLot fullParkingLot;

    @Before
    public void setUp() {
        parkingBoy = new ParkingBoy(new FirstEmptyStrategy());
        emptyParkingLot = new ParkingLot(1);
        fullParkingLot = new ParkingLot(0);
        car = new Car("BMW: Z-4");
    }

    @Test
    public void should_parking_car_to_available_parking_lot() throws Exception {
        parkingBoy.addParkables(of(emptyParkingLot));
        Ticket ticket = parkingBoy.park(car);
        assertThat(emptyParkingLot.isValid(ticket), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_parking_if_there_is_no_parking_lot_available() throws Exception {
        parkingBoy.addParkables(of(fullParkingLot));
        parkingBoy.park(car);
    }

    @Test(expected = IllegalStateException.class)
    public void should_not_parking_if_there_is_no_parking_lot() throws Exception {
        parkingBoy.park(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_car_if_ticket_is_invalid_for_all() throws Exception {
        parkingBoy.addParkables(of(fullParkingLot, emptyParkingLot));
        parkingBoy.getCar(new Ticket());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_car_if_ticket_expired() throws Exception {
        parkingBoy.addParkables(of(emptyParkingLot));
        Ticket ticket = emptyParkingLot.park(new Car("BMW: Z-4"));
        emptyParkingLot.getCar(ticket);
        parkingBoy.getCar(ticket);
    }

    @Test
    public void should_get_car_if_ticket_is_valid() throws Exception {
        parkingBoy.addParkables(of(emptyParkingLot));
        Ticket ticket = emptyParkingLot.park(car);
        assertThat(parkingBoy.getCar(ticket), is(car));
    }

    @Test
    public void should_return_false_if_ticket_is_invalid() throws Exception {
        assertThat(parkingBoy.isValid(new Ticket()), is(false));
    }

    @Test
    public void should_return_true_if_ticket_is_valid() throws Exception {
        parkingBoy.addParkables(of(emptyParkingLot));
        Ticket ticket = emptyParkingLot.park(car);
        assertThat(parkingBoy.isValid(ticket), is(true));
    }

    @Test
    public void should_return_true_if_parking_boy_has_no_parking_lots() throws Exception {
        assertThat(parkingBoy.isFull(), is(true));
    }

    @Test
    public void should_return_true_if_parking_lot_is_full() throws Exception {
        parkingBoy.addParkables(of(fullParkingLot));
        assertThat(parkingBoy.isFull(), is(true));
    }

    @Test
    public void should_return_false_if_parking_lot_is_not_full() throws Exception {
        parkingBoy.addParkables(of(emptyParkingLot));
        assertThat(parkingBoy.isFull(), is(false));
    }

    @Test
    public void should_return_0_if_there_is_no_lots() throws Exception {
        assertThat(parkingBoy.getEmptySpace(), is(0));
    }

    @Test
    public void should_return_0_if_parking_lots_are_full() throws Exception {
        parkingBoy.addParkables(of(fullParkingLot));
        assertThat(parkingBoy.getEmptySpace(), is(0));
    }

    @Test
    public void should_return_3_if_parking_lots_has_3_empty_space() throws Exception {
        ParkingLot emptyOf2 = new ParkingLot(3);
        emptyOf2.park(car);
        parkingBoy.addParkables(of(emptyParkingLot, fullParkingLot, emptyOf2));
        assertThat(parkingBoy.getEmptySpace(), is(3));
    }

    @Test
    public void should_return_0_for_empty_rate_if_there_is_no_parking_lots() throws Exception {
        assertThat(parkingBoy.getEmptyRate(), is(0.0));
    }

    @Test
    public void should_return_1_for_rate_if_all_are_empty() throws Exception {
        parkingBoy.addParkables(of(emptyParkingLot));
        assertThat(parkingBoy.getEmptyRate(), is(1.0));
    }

    @Test
    public void should_return_proper_empty_rate() throws Exception {
        ParkingLot parkingLotWith100Space = new ParkingLot(100);
        for (int i = 0; i < 20; i++) {
            parkingLotWith100Space.park(new Car("BMW: Z-4"));
        }
        ParkingLot parkingLotWith10Space = new ParkingLot(10);
        for (int i = 0; i < 5; i++) {
            parkingLotWith10Space.park(new Car("BMW: Z-4"));
        }
        parkingBoy.addParkables(of(new ParkingLot(1), parkingLotWith10Space, parkingLotWith100Space));

        assertThat(parkingBoy.getEmptyRate(), is((111.0 - 25) / 111));
    }
}
