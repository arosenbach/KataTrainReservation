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

    private static String uriBookingReferenceService = "http://localhost:8282";
    private static String uriTrainDataService = "http://localhost:8181";
    private TrainCaching trainCaching;

    public WebTicketManager() {
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();
    }

    public String reserve(String trainId, int requestedSeatsCount) {
        final Train trainInst = new Train(getTrainTopology(trainId));
        if ((trainInst.getReservedSeats() + requestedSeatsCount) <= Math.floor(ThreasholdManager.getMaxRes() * trainInst.getMaxSeat())) {
            int numberOfReserv = 0;

            // find seats to reserve
            final List<Seat> availableSeats = new ArrayList<>();
            for (int index = 0, i = 0; index < trainInst.getSeats().size(); index++) {
                Seat each = (Seat) trainInst.getSeats().toArray()[index];
                if (each.getBookingRef() == "") {
                    i++;
                    if (i <= requestedSeatsCount) {
                        availableSeats.add(each);
                    }
                }
            }

            int count = 0;
            for (Seat a : availableSeats) {
                count++;
            }

            int reservedSeats = 0;

            if (count != requestedSeatsCount) {
                return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", trainId);
            } else {
                StringBuilder sb = new StringBuilder("{\"trainId\": \"");
                sb.append(trainId);
                sb.append("\",");

                Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
                String bookingRef = getBookRef(client);

                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                    reservedSeats++;
                }

                sb.append("\"bookingReference\": \"");
                sb.append(bookingRef);
                sb.append("\",");

                if (numberOfReserv == requestedSeatsCount) {
                    trainCaching.save(trainId, trainInst, bookingRef);

                    client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
                    WebTarget uri = client.target(uriTrainDataService).path("reserve");
                    Invocation.Builder request = uri.request(MediaType.APPLICATION_JSON);

                    if (reservedSeats == 0) {
                        System.out.println("Reserved seat(s): " + reservedSeats);
                    }

                    // HTTP POST
                    Response post = request.post(Entity.entity(buildPostContent(trainId, bookingRef, availableSeats), MediaType.APPLICATION_JSON));

                    assert post.getStatus() == Response.Status.OK.getStatusCode();

                    sb.append("\"seats\":");
                    sb.append(dumpSeats(availableSeats));
                    sb.append("}");


                    return sb.toString();
                }
            }
        }

        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", trainId);

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


    private String getTrainTopology(String train) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uriTrainDataService).path("data_for_train/" + train);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }


    private String getBookRef(Client client) {
        WebTarget webTarget = client.target(uriBookingReferenceService).path("booking_reference");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }

}
