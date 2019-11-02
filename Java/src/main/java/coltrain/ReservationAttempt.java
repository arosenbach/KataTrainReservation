package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class ReservationAttempt {
    private final List<Seat> availableSeats;
    private int requestedSeatsCount;

    public ReservationAttempt(final int requestedSeatsCount, final List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
        this.requestedSeatsCount = requestedSeatsCount;
    }

    public List<Seat> getAvailableSeats() {
        return this.availableSeats;
    }

    public boolean isFulfilled() {
        return getAvailableSeats().size() == this.requestedSeatsCount;
    }

    public void assignBookingReference(final String bookingRef) {
        for (Seat availableSeat : getAvailableSeats()) {
            availableSeat.setBookingRef(bookingRef);
        }
    }
}
