package coltrain.infra;

import coltrain.domain.Seat;
import coltrain.domain.Reservation;

import java.util.List;

public class ReservationAdapter {

    public static String toJSON(Reservation reservation) {
        return "{\"trainId\": \"" + reservation.getTrainId() + "\"," +
                "\"bookingReference\": \"" + reservation.getBookingReference() + "\"," +
                "\"seats\":" + dumpSeats(reservation.getSeats()) +
                "}";
    }

    private static String dumpSeats(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat seat : seats) {
            if (!firstTime) {
                sb.append(", ");
            } else {
                firstTime = false;
            }

            sb.append("\"")
                    .append(seat.getSeatNumber())
                    .append(seat.getCoachName())
                    .append("\"");
        }

        sb.append("]");

        return sb.toString();
    }
}