package domain.models;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final List<Seat> seats;

    public Coach() {
        this(new ArrayList<>());
    }

    public Coach(List<Seat> seats) {
        this.seats = seats;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public List<Seat> getSeats() {
        return this.seats;
    }
}
