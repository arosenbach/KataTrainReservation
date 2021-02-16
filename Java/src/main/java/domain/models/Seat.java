package domain.models;

public class Seat {
    private final String coach;
    private final int seatNumber;
    private String bookingRef = "";

    public Seat(String coach, int seatNumber) {
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
        return "".equals(bookingRef);
    }

    public boolean isBooked() {
        return !isAvailable();
    }

    @Override
    public String toString() {
        return "Seat{" +
                "coach='" + coach + '\'' +
                ", seatNumber=" + seatNumber +
                ", bookingRef='" + bookingRef + '\'' +
                '}';
    }
}