package coltrain;

import domain.models.Seat;
import domain.models.Train;
import domain.service.TrainDataService;
import impl.TrainDataServiceImpl;

import java.util.List;

class FakeTrainDataService implements TrainDataService {
    private final String trainTopology;

    public FakeTrainDataService(String trainTopology) {
        this.trainTopology = trainTopology;
    }

    @Override
    public Train getTrain(String train) {
        return TrainDataServiceImpl.fromJson(trainTopology);
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
    }
}
