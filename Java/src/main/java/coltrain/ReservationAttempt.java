package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class ReservationAttempt {
    private final List<Seat> availableSeats;

    public ReservationAttempt(final List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Seat> getAvailableSeats() {
        return this.availableSeats;
    }
}
