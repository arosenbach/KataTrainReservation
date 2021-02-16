package domain.models;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class Train {
    private final List<Coach> coaches;
    private int reservedSeats;
    private int maxSeat = 0;


    public Train(final String trainTopol) {
        coaches = fromJson(trainTopol);
    }

    private List<Coach> fromJson(String trainTopol) {
        Seat e;
        //  sample
        //  {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"},
        //  "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}

        JsonObject parsed = Json.createReader(new StringReader(trainTopol)).readObject();

        final Set<Map.Entry<String, JsonValue>> allStuffs = parsed.getJsonObject("seats").entrySet();


        Map<String, Coach> seatsByCoachMap = new LinkedHashMap<>();
        this.reservedSeats = 0;
        for (Map.Entry<String, JsonValue> stuff : allStuffs) {

            final JsonObject seat = stuff.getValue().asJsonObject();
            String coachString = seat.getString("coach");
            Coach coach = seatsByCoachMap.get(coachString);
            if (coach == null) {
                coach = new Coach();
                seatsByCoachMap.put(coachString, coach);
            }

            e = new Seat(coachString, Integer.parseInt(seat.getString("seat_number")));
            coach.addSeat(e);
            if (!seat.getString("booking_reference").isEmpty()) {
                this.reservedSeats++;
            }
            this.maxSeat++;

            if (!seat.getString("booking_reference").isEmpty()) {
                e.setBookingRef(seat.getString("booking_reference"));
            }
        }
        return new ArrayList<>(seatsByCoachMap.values());
    }

    public List<Seat> getSeats() {
        return this.coaches.stream()
                .flatMap(c -> c.getSeats().stream())
                .collect(Collectors.toList());
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
//            if (availableSeats.size() >= seats) {
//                return availableSeats;
//            }
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
