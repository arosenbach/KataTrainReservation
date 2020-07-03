package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class WebTicketManager {

    static String uriBookingReferenceService = "http://localhost:8282";
    static String uriTrainDataService = "http://localhost:8181";
    private final TrainDataService trainDataService;
    private TrainCaching trainCaching;
    private BookingReferenceService bookingReferenceService;

    public WebTicketManager(TrainDataService trainDataService, BookingReferenceService bookingReferenceService) {
        this.bookingReferenceService = bookingReferenceService;
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();
        this.trainDataService = trainDataService;
    }

    public WebTicketManager() {
        this(new TrainDataServiceImpl(), new BookingReferenceServiceImpl());
    }

    public String reserve(String trainId, int seats) {
        Train trainInst = getTrain(trainId);

        if (trainInst.canBook(seats)) {

            final List<Seat> availableSeats = trainInst.getSeats(seats);

            if (availableSeats.size() != seats) {
                return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", trainId);
            } else {

                String bookingRef = bookingReferenceService.getBookRef();

                int numberOfReserv = 0;
                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                }

                if (numberOfReserv == seats) {
                    trainCaching.save(trainId, trainInst, bookingRef);

                    trainDataService.doReservation(trainId, availableSeats, bookingRef);

                    return getResponse(trainId, availableSeats, bookingRef);
                }
            }
        }

        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", trainId);
    }

    private String getResponse(String trainId, List<Seat> availableSeats, String bookingRef) {
        StringBuilder sb = new StringBuilder("{\"trainId\": \"");
        sb.append(trainId);
        sb.append("\",");
        sb.append("\"bookingReference\": \"");
        sb.append(bookingRef);
        sb.append("\",");
        sb.append("\"seats\":");
        sb.append(dumpSeats(availableSeats));
        sb.append("}");
        return sb.toString();
    }

    private Train getTrain(String trainId) {
        // get the train
        String jsonTrain = trainDataService.getTrain(trainId);
        return new Train(jsonTrain);
    }

    private String dumpSeats(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat seat : seats) {
            if (!firstTime) {
                sb.append(", ");
            } else {
                firstTime = false;
            }

            sb.append(String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()));
        }

        sb.append("]");

        return sb.toString();
    }


}
