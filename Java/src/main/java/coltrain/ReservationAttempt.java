package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ReservationAttempt {
    private final List<Seat> availableSeats;
    private final String trainId;
    private int requestedSeatsCount;
    private String bookingReference;

    public ReservationAttempt(final String trainId, final int requestedSeatsCount, final List<Seat> availableSeats) {
        this.trainId = trainId;
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
        this.bookingReference = bookingRef;
        for (Seat availableSeat : getAvailableSeats()) {
            availableSeat.setBookingRef(bookingRef);
        }
    }

    public Reservation confirm() {
        final List<Seat> reservedSeats = availableSeats
                .stream()
                .filter(Seat::isReserved)
                .filter(s -> s.getBookingRef().equals(this.bookingReference))
                .collect(toList());
        return new Reservation(this.trainId, this.bookingReference, reservedSeats);
    }
}
