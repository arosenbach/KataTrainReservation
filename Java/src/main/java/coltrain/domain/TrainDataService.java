package coltrain.domain;

import java.util.List;

public interface TrainDataService {
    Train getTrain(String trainId);

    void bookSeats(String trainId, List<Seat> availableSeats, String bookingRef);
}
