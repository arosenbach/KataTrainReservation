package coltrain;

import coltrain.api.models.Seat;

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
        final Train train = trainDataService.getTrain(trainId);
        if (train.doNotExceedCapacityThreshold(requestedSeatsCount)) {

            final List<Seat> availableSeats = train.findAvailableSeats(requestedSeatsCount);

            if (availableSeats.size() == requestedSeatsCount) {

                String bookingRef = bookingReferenceService.getBookingReference();

                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                }

                trainCaching.save(trainId, train, bookingRef);
                trainDataService.bookSeats(trainId, availableSeats, bookingRef);


                return "{\"trainId\": \"" + trainId + "\"," +
                        "\"bookingReference\": \"" + bookingRef + "\"," +
                        "\"seats\":" + dumpSeats(availableSeats) +
                        "}";
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
