package coltrain;

import coltrain.api.models.Seat;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrainDataServiceImpl implements TrainDataService {
    private String uriTrainDataService;

    public TrainDataServiceImpl(final String uriTrainDataService) {
        this.uriTrainDataService = uriTrainDataService;
    }

    public static List<Seat> adaptTrainTopology(final String trainTopol) {
        //  sample
        //  {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"},
        //  "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}

        final JsonObject parsed = Json.createReader(new StringReader(trainTopol)).readObject();

        final Set<Map.Entry<String, JsonValue>> allStuffs = parsed.getJsonObject("seats").entrySet();


        final List<Seat> seats = new ArrayList<>();
        for (Map.Entry<String, JsonValue> stuff : allStuffs) {
            final JsonObject seatJSon = stuff.getValue().asJsonObject();
            final Seat seat = new Seat(Integer.parseInt(seatJSon.getString("seat_number")), seatJSon.getString("coach"));
            seats.add(seat);

            if (!seatJSon.getString("booking_reference").isEmpty()) {
                seat.setBookingRef(seatJSon.getString("booking_reference"));
            }
        }
        return seats;
    }

    @Override
    public Train getTrain(String trainId) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uriTrainDataService).path("data_for_train/" + trainId);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        final String trainTopology = response.readEntity(String.class);
        return new Train(trainId, adaptTrainTopology(trainTopology));
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