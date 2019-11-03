package coltrain;

import coltrain.domain.BookingReferenceService;
import coltrain.domain.Reservation;
import coltrain.domain.ReservationAttempt;
import coltrain.domain.ReservationFailure;
import coltrain.domain.Train;
import coltrain.domain.TrainDataService;
import coltrain.infra.BookingReferenceServiceImpl;
import coltrain.infra.TrainDataServiceImpl;

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

    public Reservation reserve(String trainId, int requestedSeatsCount) {
        final Train train = trainDataService.getTrain(trainId);
        if (train.doNotExceedCapacityThreshold(requestedSeatsCount)) {

            final ReservationAttempt reservationAttempt = train.buildReservationAttempt(requestedSeatsCount);

            if (reservationAttempt.isFulfilled()) {

                String bookingRef = bookingReferenceService.getBookingReference();

                reservationAttempt.assignBookingReference(bookingRef);

                trainDataService.bookSeats(trainId, reservationAttempt.getAvailableSeats(), bookingRef);

                return reservationAttempt.confirm();
            }
        }

//        return "{\"trainId\": \"" + trainId + "\",\"bookingReference\": \"\",\"seats\":[]}";
        return new ReservationFailure(trainId);

    }


}
