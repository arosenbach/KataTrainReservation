package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

class FakeTrainDataService implements TrainDataService {
    @Override
    public String getTrain(String train) {
        return "{\"seats\": {\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}}}";
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
    }
}
