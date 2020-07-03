package coltrain.api.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Reservation {

    private final String trainId;
    private final List<Seat> availableSeats;
    private final String bookingRef;

    public static Reservation emptyReservation(String trainId) {
        return new Reservation(trainId, Collections.<Seat>emptyList(), "");
    }

    public Reservation(String trainId, List<Seat> availableSeats, String bookingRef) {
        this.trainId = trainId;
        this.availableSeats = availableSeats;
        this.bookingRef = bookingRef;
    }

    public String getTrainId() {
        return trainId;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return trainId.equals(that.trainId) &&
                Objects.equals(availableSeats, that.availableSeats) &&
                Objects.equals(bookingRef, that.bookingRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, availableSeats, bookingRef);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "trainId='" + trainId + '\'' +
                ", availableSeats=" + availableSeats +
                ", bookingRef='" + bookingRef + '\'' +
                '}';
    }
}
