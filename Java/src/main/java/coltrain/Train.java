package coltrain;

import coltrain.api.models.Seat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Train {
    private static final double CAPACITY_THRESHOLD = 0.70;
    private HashMap<String, Coach> coaches;

    public Train(final List<Seat> seats) {
        this.coaches = new HashMap<>();

        for (Seat seat : seats) {
            final String coachName = seat.getCoachName();
            if (!coaches.containsKey(coachName)) {
                coaches.put(coachName, new Coach(coachName));
            }
            coaches.get(coachName).addSeat(seat);
        }

    }

    public List<Seat> getSeats() {
        return this.coaches
                .values()
                .stream()
                .map(Coach::getSeats)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public int getReservedSeats() {
        return Math.toIntExact(this.getSeats().stream().filter(s -> !s.getBookingRef().isEmpty()).count());
    }

    public int getMaxSeat() {
        return this.getSeats().size();
    }

    public boolean doNotExceedCapacityThreshold(final int requestedSeatsCount) {
        return (getReservedSeats() + requestedSeatsCount) <= Math.floor(CAPACITY_THRESHOLD * getMaxSeat());
    }

    public ReservationAttempt buildReservationAttempt(final int requestedSeatsCount) {
        final List<Seat> availableSeats = new ArrayList<>();
        for (int index = 0, i = 0; index < getSeats().size(); index++) {
            Seat each = (Seat) getSeats().toArray()[index];
            if (each.getBookingRef().equals("")) {
                i++;
                if (i <= requestedSeatsCount) {
                    availableSeats.add(each);
                }
            }
        }
        return new ReservationAttempt(requestedSeatsCount, availableSeats);
    }

    public Map<String, Coach> getCoaches() {
        return this.coaches;
    }
}
