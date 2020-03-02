package coltrain;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import coltrain.api.models.Seat;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class WebTicketManager {

    static String uriBookingReferenceService = "http://localhost:8282";
    private static String uriTrainDataService = "http://localhost:8181";
    private final Dependency dependency = new Dependency();
    private TrainCaching trainCaching;

    public WebTicketManager() {
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();
    }

    public String reserve(String train, int seats) {
        List<Seat> availableSeats = new ArrayList<Seat>();
        int count = 0;
        String result = "";
        String bookingRef;

        // get the train
        String jsonTrain = getTrain(train);

        result = jsonTrain;

        Train trainInst = new Train(jsonTrain);
        if ((trainInst.getReservedSeats() + seats) <= Math.floor(ThreasholdManager.getMaxRes() * trainInst.getMaxSeat())) {
            int numberOfReserv = 0;

            // find seats to reserve
            for (int index = 0, i = 0; index < trainInst.getSeats().size(); index++) {
                Seat each = (Seat) trainInst.getSeats().toArray()[index];
                if (each.getBookingRef() == "") {
                    i++;
                    if (i <= seats) {
                        availableSeats.add(each);
                    }
                }
            }

            for (Seat a : availableSeats) {
                count++;
            }

            int reservedSeats = 0;

            if (count != seats) {
                return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", train);
            } else {
                StringBuilder sb = new StringBuilder("{\"trainId\": \"");
                sb.append(train);
                sb.append("\",");

                Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
                bookingRef = dependency.getBookRef(client);

                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                    reservedSeats++;
                }

                sb.append("\"bookingReference\": \"");
                sb.append(bookingRef);
                sb.append("\",");

                if (numberOfReserv == seats) {
                    trainCaching.save(train, trainInst, bookingRef);

                    client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
                    WebTarget uri = client.target(uriTrainDataService).path("reserve");
                    Invocation.Builder request = uri.request(MediaType.APPLICATION_JSON);

                    String todo = "[TODO]";

                    if (reservedSeats == 0) {
                        System.out.println("Reserved seat(s): " + reservedSeats);
                    }

                    // HTTP POST
                    Response post = request.post(Entity.entity(buildPostContent(train, bookingRef, availableSeats), MediaType.APPLICATION_JSON));

                    assert post.getStatus() == Response.Status.OK.getStatusCode();

                    sb.append("\"seats\":");
                    sb.append(dumpSeats(availableSeats));
                    sb.append("}");


                    return sb.toString();
                }
            }
        }

        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", train);

    }

    private String dumpSeats(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat seat : seats) {
            if (!firstTime) {
                sb.append(", ");
            } else {
                firstTime = false;
            }

            sb.append(String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()));
        }

        sb.append("]");

        return sb.toString();
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


        String result = String.format("{\"trainId\": \"%s\", \"bookingReference\": \"%s\", \"seats\":%s}",
                trainId,
                bookingRef,
                seats.toString());
        return result;

    }


    private String getTrain(String train) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uriTrainDataService).path("data_for_train/" + train);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }

}
