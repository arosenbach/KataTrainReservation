package coltrain.infra;

import coltrain.IReservationService;
import coltrain.domain.Seat;
import coltrain.domain.Reservation;

import java.util.List;

public class ReservationAdapter {

    private final IReservationService reservationService;

    public ReservationAdapter(final IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    private String toJSON(Reservation reservation) {
        return "{\"trainId\": \"" + reservation.getTrainId() + "\"," +
                "\"bookingReference\": \"" + reservation.getBookingReference() + "\"," +
                "\"seats\":" + dumpSeats(reservation.getSeats()) +
                "}";
    }

    private String dumpSeats(List<Seat> seats) {
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

    public String post(final ReservationRequestDTO reservationRequestDTO) {
        // Adapt from infra to domain
        final String trainId = reservationRequestDTO.getTrainId();
        final int numberOfSeats = reservationRequestDTO.getNumberOfSeats();

        // Call business logic
        final Reservation reservation = reservationService.reserve(trainId, numberOfSeats);

        // Adapt from domain to infra
        return  toJSON(reservation);


    }
}