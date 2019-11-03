package coltrain.domain;

public class Seat {
    private final String coach;
    private final int seatNumber;
    private String bookingRef = "";

    public Seat(int seatNumber, String coach) {
        this.coach = coach;
        this.seatNumber = seatNumber;
    }

    public boolean equals(Object o) {
        Seat other = (Seat) o;
        return coach.equals(other.getCoachName()) && seatNumber == other.getSeatNumber();
    }

    public String getBookingRef() {
        return this.bookingRef;
    }

    public void setBookingRef(final String bookingRef) {
        this.bookingRef = bookingRef;
    }

    public int getSeatNumber() {
        return this.seatNumber;
    }

    public String getCoachName() {
        return this.coach;
    }

    public boolean isAvailable() {
        return this.bookingRef.equals("");
    }

    public  boolean isReserved() {
        return !this.bookingRef.equals("");
    }

    @Override
    public String toString() {
        return seatNumber + coach;
    }
}