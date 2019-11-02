package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class WebTicketManager {

    private static String uriBookingReferenceService = "http://localhost:8282";
    private static String uriTrainDataService = "http://localhost:8181";
    private final TrainDataService trainDataService;
    private BookingReferenceService bookingReferenceService;

    public WebTicketManager(final TrainDataService trainDataService, final BookingReferenceService bookingReferenceService) {
        this.trainDataService = trainDataService;
        this.bookingReferenceService = bookingReferenceService;
    }

    public WebTicketManager() {
        this(new TrainDataServiceImpl(WebTicketManager.uriTrainDataService), new BookingReferenceServiceImpl(WebTicketManager.uriBookingReferenceService));
    }

    public String reserve(String trainId, int requestedSeatsCount) {
        final Train train = trainDataService.getTrain(trainId);
        if (train.doNotExceedCapacityThreshold(requestedSeatsCount)) {

            final ReservationAttempt reservationAttempt = train.buildReservationAttempt(requestedSeatsCount);

            if (reservationAttempt.isFulfilled()) {

                String bookingRef = bookingReferenceService.getBookingReference();

                reservationAttempt.assignBookingReference(bookingRef);

                trainDataService.bookSeats(trainId, reservationAttempt.getAvailableSeats(), bookingRef);


                return "{\"trainId\": \"" + trainId + "\"," +
                        "\"bookingReference\": \"" + bookingRef + "\"," +
                        "\"seats\":" + dumpSeats(reservationAttempt.getAvailableSeats()) +
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
