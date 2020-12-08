package domain.service;

import domain.models.Seat;
import domain.models.Train;

import java.util.List;

public interface TrainDataService {

    Train getTrain(String train);

    void doReservation(String train, List<Seat> availableSeats, String bookingRef);
}
