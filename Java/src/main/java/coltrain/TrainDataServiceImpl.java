package coltrain;

import coltrain.api.models.Seat;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class TrainDataServiceImpl implements TrainDataService {
    private String uriTrainDataService;

    public TrainDataServiceImpl(final String uriTrainDataService) {
        this.uriTrainDataService = uriTrainDataService;
    }

    @Override
    public String getTrainTopology(String train) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uriTrainDataService).path("data_for_train/" + train);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }

    @Override
    public void bookSeats(final String trainId, final List<Seat> availableSeats, final String bookingRef) {
        final Client client;
        client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget uri = client.target(uriTrainDataService).path("reserve");
        Invocation.Builder request = uri.request(MediaType.APPLICATION_JSON);


        // HTTP POST
        Response post = request.post(Entity.entity(buildPostContent(trainId, bookingRef, availableSeats), MediaType.APPLICATION_JSON));

        assert post.getStatus() == Response.Status.OK.getStatusCode();
    }

    private static String buildPostContent(String trainId, String bookingRef, List<Seat> availableSeats) {
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
}