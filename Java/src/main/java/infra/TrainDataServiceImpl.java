package infra;

import domain.models.Coach;
import domain.models.Seat;
import domain.models.Train;
import domain.service.TrainDataService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrainDataServiceImpl implements TrainDataService {
    private static final String TRAIN_DATA_SERVICE_URL = "http://localhost:8181";

    public static Train fromJson(String trainTopol) {
        //  sample
        //  {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"},
        //  "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}

        JsonObject jsonObject = Json.createReader(new StringReader(trainTopol)).readObject();

        final Set<Map.Entry<String, JsonValue>> jsonSeats = jsonObject.getJsonObject("seats").entrySet();


        Map<String, Coach> seatsByCoachMap = new LinkedHashMap<>();
        for (Map.Entry<String, JsonValue> jsonSeatEntry : jsonSeats) {

            final JsonObject jsonSeat = jsonSeatEntry.getValue().asJsonObject();
            String coachId = jsonSeat.getString("coach");
            if (!seatsByCoachMap.containsKey(coachId)) {
                seatsByCoachMap.put(coachId, new Coach());
            }

            String bookingReference = jsonSeat.getString("booking_reference");
            Seat seat = new Seat(coachId, Integer.parseInt(jsonSeat.getString("seat_number")));
            seat.setBookingRef(bookingReference);

            Coach coach = seatsByCoachMap.get(coachId);
            coach.addSeat(seat);
        }
        return new Train(seatsByCoachMap.values());
    }

    @Override
    public Train getTrain(String train) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(TRAIN_DATA_SERVICE_URL).path("data_for_train/" + train);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        String trainJson = response.readEntity(String.class);

        return fromJson(trainJson);
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


        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"%s\", \"seats\":%s}",
                trainId,
                bookingRef,
                seats.toString());

    }
}