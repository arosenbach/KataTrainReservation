package coltrain.test.acceptance;

import coltrain.BookingReferenceService;
import coltrain.TrainDataService;
import coltrain.WebTicketManager;
import coltrain.api.models.Seat;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ColtrainTest {

    public static final String BOOKING_REFERENCE = "75bcd15";
    private static final String EMPTY_BOOKING = "";

    @Test
    public void should_reserve_seats_when_train_is_empty() {
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.EMPTY_TRAIN);
        final FakeBookingReferenceService bookingReferenceService = new FakeBookingReferenceService(BOOKING_REFERENCE);
        final WebTicketManager sut = new WebTicketManager(trainDataService, bookingReferenceService);

        final String reservation = sut.reserve("express_2000", 3);

        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"" + BOOKING_REFERENCE + "\",\"seats\":[\"1A\", \"2A\", \"3A\"]}", reservation);
    }

    @Test
    public void should_not_reserve_seats_when_train_is_70_percent_booked() {
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.WITH_10_SEATS_AND_6_ALREADY_BOOKED);
        final FakeBookingReferenceService bookingReferenceService = new FakeBookingReferenceService(BOOKING_REFERENCE);
        final WebTicketManager sut = new WebTicketManager(trainDataService, bookingReferenceService);

        final String reservation = sut.reserve("express_2000", 3);

        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"" + EMPTY_BOOKING + "\",\"seats\":[]}", reservation);
    }

    private class FakeTrainDataService implements TrainDataService {
        private String topology;

        private FakeTrainDataService(final String topology) {
            this.topology = topology;
        }

        @Override
        public String getTrainTopology(final String train) {
            return topology;
        }

        @Override
        public void bookSeats(final String trainId, final List<Seat> availableSeats, final String bookingRef) {

        }
    }

    private class FakeBookingReferenceService implements BookingReferenceService {
        private final String bookingReference;

        public FakeBookingReferenceService(final String bookingReference) {
            this.bookingReference = bookingReference;
        }

        @Override
        public String getBookingReference() {
            return bookingReference;
        }
    }
}