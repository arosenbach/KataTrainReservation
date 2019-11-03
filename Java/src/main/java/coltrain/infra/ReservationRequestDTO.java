package coltrain.infra;

public class ReservationRequestDTO {
    private String trainId;
    private int numberOfSeats;

    public String getTrainId() {
        return this.trainId;
    }

    public void setTrainId(final String trainId) {
        this.trainId = trainId;
    }

    public void setNumberOfSeats(final int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }
}
