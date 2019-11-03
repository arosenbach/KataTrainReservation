package coltrain.domain;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private List<Seat> seats;
    private String trainId;

    public Coach(final String trainId) {
        this.trainId = trainId;
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

        return new ReservationAttempt(trainId, requestedSeatsCount, availableSeats);
    }
}
