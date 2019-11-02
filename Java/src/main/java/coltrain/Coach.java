package coltrain;

import coltrain.api.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final String coachName;
    private List<Seat> seats;

    public Coach(final String coachName) {
        this.coachName = coachName;
        this.seats = new ArrayList<>();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void addSeat(final Seat seat) {
        seats.add(seat);
    }

    public ReservationAttempt buildReservationAttempt(final int requestedSeatsCount) {
        final List<Seat> availableSeats = new ArrayList<>();

        int addedSeats = 0;
        for(Seat seat: getSeats()){
            if (seat.isAvailable()) {
                if (addedSeats < requestedSeatsCount) {
                    availableSeats.add(seat);
                    addedSeats++;
                }
            }
        }

        return new ReservationAttempt(requestedSeatsCount, availableSeats);
    }
}
