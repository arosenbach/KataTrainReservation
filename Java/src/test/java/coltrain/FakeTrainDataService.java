package coltrain;

import domain.models.Seat;
import domain.service.TrainDataService;

import java.util.List;

class FakeTrainDataService implements TrainDataService {
    private String trainTopology;

    public FakeTrainDataService(String trainTopology) {
        this.trainTopology = trainTopology;
    }

    @Override
    public String getTrain(String train) {
        return trainTopology;
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
    }
}
