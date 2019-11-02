package coltrain;

import coltrain.api.models.Seat;

import java.util.List;
import java.util.Objects;

public class Reservation {

    private String bookingReference;
    private String trainId;
    private List<Seat> seats;

    public Reservation(final String trainId, final String bookingReference, final List<Seat> seats) {
        this.bookingReference = bookingReference;
        this.trainId = trainId;
        this.seats = seats;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public String getTrainId() {
        return trainId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Reservation that = (Reservation) o;
        return Objects.equals(bookingReference, that.bookingReference) &&
                Objects.equals(trainId, that.trainId) &&
                Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingReference, trainId, seats);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "bookingReference='" + bookingReference + '\'' +
                ", trainId='" + trainId + '\'' +
                ", seats=" + seats +
                '}';
    }
}
