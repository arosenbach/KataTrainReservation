package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public interface TrainDataService {

    String getTrain(String train);

    void doReservation(String train, List<Seat> availableSeats, String bookingRef);
}
