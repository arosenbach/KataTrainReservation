package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public interface TrainDataService {
    Train getTrain(String trainId);

    void bookSeats(String trainId, List<Seat> availableSeats, String bookingRef);
}
