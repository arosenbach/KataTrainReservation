package domain.models;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.*;

public class Train {
    private final List<Coach> coaches;
    private int reservedSeats;
    private int maxSeat;

    public Train(int maxSeats, int reservedSeats, Collection<Coach> coaches) {
        this.maxSeat = maxSeats;
        this.reservedSeats = reservedSeats;
        this.coaches = new ArrayList<>(coaches);
    }

    public static Train fromJson(String trainTopol) {
        //  sample
        //  {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"},
        //  "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}

        JsonObject jsonObject = Json.createReader(new StringReader(trainTopol)).readObject();

        final Set<Map.Entry<String, JsonValue>> jsonSeats = jsonObject.getJsonObject("seats").entrySet();


        Map<String, Coach> seatsByCoachMap = new LinkedHashMap<>();
        int reservedSeats = 0;
        int maxSeats = 0;
        for (Map.Entry<String, JsonValue> jsonSeatEntry : jsonSeats) {

            final JsonObject jsonSeat = jsonSeatEntry.getValue().asJsonObject();
            String coachId = jsonSeat.getString("coach");
            if (!seatsByCoachMap.containsKey(coachId)) {
                seatsByCoachMap.put(coachId, new Coach());
            }

            Seat seat = new Seat(coachId, Integer.parseInt(jsonSeat.getString("seat_number")));
            Coach coach = seatsByCoachMap.get(coachId);
            coach.addSeat(seat);

            boolean isAvailable = jsonSeat.getString("booking_reference").isEmpty();
            if (!isAvailable) {
                reservedSeats++;
            }
            maxSeats++;

            if (!isAvailable) {
                seat.setBookingRef(jsonSeat.getString("booking_reference"));
            }
        }
        return new Train(maxSeats, reservedSeats, seatsByCoachMap.values());
    }

    public int getReservedSeats() {
        return this.reservedSeats;
    }

    public int getMaxSeat() {
        return this.maxSeat;
    }

    public List<Seat> getSeats(int seats) {
        // find seats to reserve
        List<Seat> availableSeats = new ArrayList<>();
        int assignedSeats = 0;
        for (Coach coach : coaches) {
            List<Seat> coachAvailableSeats = coach.getAvailableSeats();
            if (coachAvailableSeats.size() >= seats) {
                return coachAvailableSeats.subList(0, seats);
            }
            for (Seat s : coachAvailableSeats) {
                if (assignedSeats < seats) {
                    availableSeats.add(s);
                    assignedSeats++;
                }
            }
        }
        return availableSeats;
    }

    public boolean canBook(int seats) {
        return (getReservedSeats() + seats) <= Math.floor(ThreasholdManager.getMaxRes() * getMaxSeat());
    }
}
