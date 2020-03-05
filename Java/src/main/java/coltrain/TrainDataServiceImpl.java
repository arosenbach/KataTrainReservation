package coltrain;

import coltrain.api.models.Seat;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

public class TrainDataServiceImpl implements TrainDataService {
    final String uri;

    public TrainDataServiceImpl(String uri) {
        this.uri = uri;
    }

    private String buildPostContent(String trainId, String bookingRef, List<Seat> availableSeats) {
        StringBuilder seats = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat s : availableSeats) {
            if (!firstTime) {
                seats.append(", ");
            } else {
                firstTime = false;
            }

            seats.append(String.format("\"%s%s\"", s.getSeatNumber(), s.getCoachName()));
        }

        seats.append("]");


        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"%s\", \"seats\":%s}",
                trainId,
                bookingRef,
                seats.toString());

    }

    @Override
    public String getTrain(String train) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uri).path("data_for_train/" + train);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
        Client client;
        client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget uri = client.target(this.uri).path("reserve");
        Invocation.Builder request = uri.request(MediaType.APPLICATION_JSON);


        // HTTP POST
        Response post = request.post(Entity.entity(buildPostContent(train, bookingRef, availableSeats), MediaType.APPLICATION_JSON));

        assert post.getStatus() == Response.Status.OK.getStatusCode();
    }
}
