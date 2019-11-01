package coltrain;

import coltrain.api.models.Seat;

import java.util.*;

public class Train {
    private final List<Seat> seats;

    public Train(final List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public int getReservedSeats() {
        return Math.toIntExact(this.seats.stream().filter(s -> !s.getBookingRef().isEmpty()).count());
    }

    public int getMaxSeat() {
        return this.seats.size();
    }
}
