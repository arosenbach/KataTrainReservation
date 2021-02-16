package impl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import domain.service.BookingReferenceService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookingReferenceServiceImpl implements BookingReferenceService {
    public static String uriBookingReferenceService = "http://localhost:8282";
    public static String uriTrainDataService = "http://localhost:8181";

    @Override
    public String getBookRef() {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uriBookingReferenceService).path("booking_reference");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }
}