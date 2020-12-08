package coltrain;

import models.Reservation;
import models.Seat;
import models.Train;
import service.ReservationManager;

import java.util.Collections;
import java.util.List;

public class WebTicketManager implements ReservationManager {

    static String uriBookingReferenceService = "http://localhost:8282";
    static String uriTrainDataService = "http://localhost:8181";
    private final TrainDataService trainDataService;
    private TrainCaching trainCaching;
    private BookingReferenceService bookingReferenceService;

    public WebTicketManager(TrainDataService trainDataService, BookingReferenceService bookingReferenceService) {
        this.bookingReferenceService = bookingReferenceService;
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();
        this.trainDataService = trainDataService;
    }

    public WebTicketManager() {
        this(new TrainDataServiceImpl(), new BookingReferenceServiceImpl());
    }

    @Override
    public Reservation reserve(String trainId, int seats) {
        Train trainInst = getTrain(trainId);

        if (trainInst.canBook(seats)) {

            final List<Seat> availableSeats = trainInst.getSeats(seats);

            if (availableSeats.size() != seats) {
                return new Reservation(trainId, Collections.<Seat>emptyList(), "");
            } else {

                String bookingRef = bookingReferenceService.getBookRef();

                int numberOfReserv = 0;
                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                }

                if (numberOfReserv == seats) {
                    trainCaching.save(trainId, trainInst, bookingRef);

                    trainDataService.doReservation(trainId, availableSeats, bookingRef);

                    return new Reservation(trainId, availableSeats, bookingRef);
                }
            }
        }

        return new Reservation(trainId, Collections.<Seat>emptyList(), "");
    }

    private Train getTrain(String trainId) {
        // get the train
        String jsonTrain = trainDataService.getTrain(trainId);
        return new Train(jsonTrain);
    }

}
