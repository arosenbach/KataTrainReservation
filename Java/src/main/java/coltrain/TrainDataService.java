package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public interface TrainDataService {
    String getTrainTopology(String train);

    void bookSeats(String trainId, List<Seat> availableSeats, String bookingRef);
}
