package coltrain.infra;

import coltrain.ReservationService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationsController {

    private final ReservationService reservationService;
    private final ReservationAdapter reservationAdapter;

    public ReservationsController() {
        reservationService = new ReservationService();
        reservationAdapter = new ReservationAdapter(this.reservationService);
    }

    @POST
    public String post(ReservationRequestDTO reservationRequest) {
        return this.reservationAdapter.post(reservationRequest);
    }

}