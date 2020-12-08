package impl;

import models.WebTicketManager;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import service.BookingReferenceService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookingReferenceServiceImpl implements BookingReferenceService {

    @Override
    public String getBookRef() {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(WebTicketManager.uriBookingReferenceService).path("booking_reference");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }
}
