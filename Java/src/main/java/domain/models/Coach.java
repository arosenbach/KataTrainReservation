package domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Seat> getAvailableSeats() {
        return this.seats.stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }
}
