package coltrain.infra;

import coltrain.domain.BookingReferenceService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookingReferenceServiceImpl implements BookingReferenceService {
    private static final String BOOKING_REFERENCE_PATH = "booking_reference";
    private String uriBookingReferenceService;

    public BookingReferenceServiceImpl(final String uriBookingReferenceService) {
        this.uriBookingReferenceService = uriBookingReferenceService;
    }

    @Override
    public String getBookingReference() {
        final Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        final WebTarget webTarget = client.target(uriBookingReferenceService).path(BOOKING_REFERENCE_PATH);
        final Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        final Response response = invocationBuilder.get();
        return response.readEntity(String.class);
    }
}