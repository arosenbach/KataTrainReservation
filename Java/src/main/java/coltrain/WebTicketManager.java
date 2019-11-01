package coltrain;

import coltrain.api.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class WebTicketManager {

    private static String uriBookingReferenceService = "http://localhost:8282";
    private static String uriTrainDataService = "http://localhost:8181";
    private final TrainDataService trainDataService;
    private TrainCaching trainCaching;
    private BookingReferenceService bookingReferenceService;

    public WebTicketManager(final TrainDataService trainDataService, final BookingReferenceService bookingReferenceService) {
        this.trainDataService = trainDataService;
        this.bookingReferenceService = bookingReferenceService;
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();
    }

    public WebTicketManager() {
        this(new TrainDataServiceImpl(WebTicketManager.uriTrainDataService), new BookingReferenceServiceImpl(WebTicketManager.uriBookingReferenceService));
    }

    public String reserve(String trainId, int requestedSeatsCount) {
        final Train trainInst = trainDataService.getTrain(trainId);
        if ((trainInst.getReservedSeats() + requestedSeatsCount) <= Math.floor(ThreasholdManager.getMaxRes() * trainInst.getMaxSeat())) {

            // find seats to reserve
            final List<Seat> availableSeats = new ArrayList<>();
            for (int index = 0, i = 0; index < trainInst.getSeats().size(); index++) {
                Seat each = (Seat) trainInst.getSeats().toArray()[index];
                if (each.getBookingRef().equals("")) {
                    i++;
                    if (i <= requestedSeatsCount) {
                        availableSeats.add(each);
                    }
                }
            }

            if (availableSeats.size() != requestedSeatsCount) {
                return String.format("{\"trainId\": \"%s\",\"bookingReference\": \"\", \"seats\":[]}", trainId);
            } else {

                String bookingRef = bookingReferenceService.getBookingReference();

                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                }

                if (availableSeats.size() == requestedSeatsCount) {
                    trainCaching.save(trainId, trainInst, bookingRef);
                    trainDataService.bookSeats(trainId, availableSeats, bookingRef);


                    return "{\"trainId\": \"" + trainId + "\"," +
                            "\"bookingReference\": \"" + bookingRef + "\"," +
                            "\"seats\":" + dumpSeats(availableSeats) +
                            "}";
                }
            }
        }

        return "{\"trainId\": \"" + trainId + "\",\"bookingReference\": \"\",\"seats\":[]}";

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

            sb.append("\"")
                    .append(seat.getSeatNumber())
                    .append(seat.getCoachName())
                    .append("\"");
        }

        sb.append("]");

        return sb.toString();
    }


}
