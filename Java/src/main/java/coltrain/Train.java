package coltrain;

import coltrain.api.models.Seat;

import java.util.*;

public class Train {
    private static final double CAPACITY_THRESHOLD = 0.70;
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

    public boolean doNotExceedCapacityThreshold(final int requestedSeatsCount) {
        return (getReservedSeats() + requestedSeatsCount) <= Math.floor(CAPACITY_THRESHOLD * getMaxSeat());
    }

    public ReservationAttempt buildReservationAttempt(final int requestedSeatsCount) {
        final List<Seat> availableSeats = new ArrayList<>();
        for (int index = 0, i = 0; index < getSeats().size(); index++) {
            Seat each = (Seat) getSeats().toArray()[index];
            if (each.getBookingRef().equals("")) {
                i++;
                if (i <= requestedSeatsCount) {
                    availableSeats.add(each);
                }
            }
        }
        return new ReservationAttempt(requestedSeatsCount, availableSeats);
    }
}
