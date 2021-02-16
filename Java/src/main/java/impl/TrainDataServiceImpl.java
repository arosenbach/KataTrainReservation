package impl;

import domain.models.Seat;
import domain.models.Train;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import domain.service.TrainDataService;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class TrainDataServiceImpl implements TrainDataService {
    private static final String TRAIN_DATA_SERVICE_URL = "http://localhost:8181";

    @Override
    public Train getTrain(String train) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(TRAIN_DATA_SERVICE_URL).path("data_for_train/" + train);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        String trainJson = response.readEntity(String.class);

        return new Train(trainJson);
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget uri = client.target(TRAIN_DATA_SERVICE_URL).path("reserve");
        Invocation.Builder request = uri.request(MediaType.APPLICATION_JSON);

        // HTTP POST
        Response post = request.post(Entity.entity(buildPostContent(train, bookingRef, availableSeats), MediaType.APPLICATION_JSON));

        assert post.getStatus() == Response.Status.OK.getStatusCode();
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


        String result = String.format("{\"trainId\": \"%s\", \"bookingReference\": \"%s\", \"seats\":%s}",
                trainId,
                bookingRef,
                seats.toString());
        return result;

    }
}