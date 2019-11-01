package coltrain.api.controllers;

import coltrain.TrainDataServiceImpl;
import coltrain.WebTicketManager;
import coltrain.api.models.ReservationRequestDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationsController {
    public ReservationsController() { }

    @POST
    public String post(ReservationRequestDTO reservationRequest) {
        final WebTicketManager manager = new WebTicketManager();
        return manager.reserve(reservationRequest.getTrainId(), reservationRequest.getNumberOfSeats());
    }

}