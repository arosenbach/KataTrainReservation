package coltrain;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookingRefServiceImpl implements BookingRefService {
    public BookingRefServiceImpl() {
    }

    @Override
    public String getBookRef(Client client) {
        WebTarget webTarget = client.target(WebTicketManager.uriBookingReferenceService).path("booking_reference");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }
}