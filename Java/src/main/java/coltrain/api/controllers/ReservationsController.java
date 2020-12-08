package coltrain.api.controllers;

import coltrain.WebTicketManager;
import models.Reservation;
import models.ReservationRequestDTO;
import models.Seat;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationsController {
    public ReservationsController() { }


    @POST
    public String post(ReservationRequestDTO reservationRequest) {
        final WebTicketManager manager = new WebTicketManager();
        return toJsonString(manager.reserve(reservationRequest.getTrainId(), reservationRequest.getNumberOfSeats()));
    }

    public static String toJsonString(Reservation reservation) {
        return "{\"trainId\": \"" + reservation.getTrainId() +
                "\"," +
                "\"bookingReference\": \"" +
                reservation.getBookingRef() +
                "\"," +
                "\"seats\":" +
                dumpSeats(reservation.getAvailableSeats()) +
                "}";
    }

    public static String dumpSeats(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat seat : seats) {
            if (!firstTime) {
                sb.append(", ");
            } else {
                firstTime = false;
            }

            sb.append(String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()));
        }

        sb.append("]");

        return sb.toString();
    }
}