package domain.models;

import java.util.*;

public class Train {
    private final List<Coach> coaches;

    public Train(Collection<Coach> coaches) {
        this.coaches = new ArrayList<>(coaches);
    }

    public int getReservedSeats() {
        return this.coaches.stream().mapToInt(it -> (it.getBookedSeats().size())).sum();
    }

    public int getMaxSeat() {
        return this.coaches.stream().mapToInt(it -> it.getSeats().size()).sum();
    }

    public List<Seat> getSeats(int seats) {
        // find seats to reserve
        for (Coach coach : coaches) {
            List<Seat> coachAvailableSeats = coach.getAvailableSeats();
            if (coachAvailableSeats.size() >= seats) {
                return coachAvailableSeats.subList(0, seats);
            }
        }
        return Collections.emptyList();
    }

    public boolean canBook(int seats) {
        return (getReservedSeats() + seats) <= Math.floor(ThreasholdManager.getMaxRes() * getMaxSeat());
    }
}
