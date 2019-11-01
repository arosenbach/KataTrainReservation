package coltrain.test.acceptance;

import coltrain.BookingReferenceService;
import coltrain.TrainDataService;
import coltrain.WebTicketManager;
import coltrain.api.models.Seat;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ColtrainTest {
    @Test
    public void should_reserve_seats_when_train_is_empty() {
        final String bookingReference = "75bcd15";
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(), new FakeBookingReferenceService(bookingReference));
        final String reservation = sut.reserve("express_2000", 3);

        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \""+bookingReference+"\",\"seats\":[\"1A\", \"2A\", \"3A\"]}", reservation);
    }

    private class FakeTrainDataService implements TrainDataService {
        @Override
        public String getTrainTopology(final String train) {
            return "{\"seats\":{" +
                    "\"1A\":{\"coach\":\"A\",\"seat_number\":\"1\",\"booking_reference\":\"\"}," +
                    "\"2A\":{\"coach\":\"A\",\"seat_number\":\"2\",\"booking_reference\":\"\"}," +
                    "\"3A\":{\"coach\":\"A\",\"seat_number\":\"3\",\"booking_reference\":\"\"}," +
                    "\"4A\":{\"coach\":\"A\",\"seat_number\":\"4\",\"booking_reference\":\"\"}," +
                    "\"5A\":{\"coach\":\"A\",\"seat_number\":\"5\",\"booking_reference\":\"\"}," +
                    "\"6A\":{\"coach\":\"A\",\"seat_number\":\"6\",\"booking_reference\":\"\"}," +
                    "\"7A\":{\"coach\":\"A\",\"seat_number\":\"7\",\"booking_reference\":\"\"}," +
                    "\"8A\":{\"coach\":\"A\",\"seat_number\":\"8\",\"booking_reference\":\"\"}," +
                    "\"1B\":{\"coach\":\"B\",\"seat_number\":\"1\",\"booking_reference\":\"\"}," +
                    "\"2B\":{\"coach\":\"B\",\"seat_number\":\"2\",\"booking_reference\":\"\"}," +
                    "\"3B\":{\"coach\":\"B\",\"seat_number\":\"3\",\"booking_reference\":\"\"}," +
                    "\"4B\":{\"coach\":\"B\",\"seat_number\":\"4\",\"booking_reference\":\"\"}," +
                    "\"5B\":{\"coach\":\"B\",\"seat_number\":\"5\",\"booking_reference\":\"\"}," +
                    "\"6B\":{\"coach\":\"B\",\"seat_number\":\"6\",\"booking_reference\":\"\"}," +
                    "\"7B\":{\"coach\":\"B\",\"seat_number\":\"7\",\"booking_reference\":\"\"}," +
                    "\"8B\":{\"coach\":\"B\",\"seat_number\":\"8\",\"booking_reference\":\"\"}}" +
                    "}";
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