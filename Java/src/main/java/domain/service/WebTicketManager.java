package domain.service;

import domain.models.Reservation;
import domain.models.Seat;
import domain.models.Train;

import java.util.List;

public class WebTicketManager implements ReservationManager {

    private final TrainDataService trainDataService;
    private final BookingReferenceService bookingReferenceService;

    public WebTicketManager(TrainDataService trainDataService, BookingReferenceService bookingReferenceService) {
        this.bookingReferenceService = bookingReferenceService;
        this.trainDataService = trainDataService;
    }

    @Override
    public Reservation reserve(String trainId, int seats) {
        Train trainInst = getTrain(trainId);

        if (trainInst.canBook(seats)) {

            final List<Seat> availableSeats = trainInst.getSeats(seats);

            if (availableSeats.size() != seats) {
                return Reservation.emptyReservation(trainId);
            } else {

                String bookingRef = bookingReferenceService.getBookRef();

                int numberOfReserv = 0;
                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                }

                if (numberOfReserv == seats) {

                    trainDataService.doReservation(trainId, availableSeats, bookingRef);

                    return new Reservation(trainId, availableSeats, bookingRef);
                }
            }
        }

        return Reservation.emptyReservation(trainId);
    }

    private Train getTrain(String trainId) {
        // get the train
        return trainDataService.getTrain(trainId);
    }

}
